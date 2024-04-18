package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.FileException;
import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Resource;
import com.hanyang.dataportal.dataset.infrastructure.S3StorageManager;
import com.hanyang.dataportal.dataset.infrastructure.dto.FileInfoDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.DownloadRepository;
import com.hanyang.dataportal.dataset.repository.ResourceRepository;
import com.hanyang.dataportal.user.domain.Download;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hanyang.dataportal.dataset.domain.vo.Type.findByType;


@Service
@RequiredArgsConstructor
public class ResourceService {

    private final S3StorageManager s3StorageManager;
    private final UserService userService;
    private final DownloadRepository downloadRepository;
    private final DatasetRepository datasetRepository;
    private final ResourceRepository resourceRepository;

    public void save(Long datasetId, MultipartFile multipartFile){
        Dataset dataset = datasetRepository.findByIdWithTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));

        Optional<Resource> optionalResource = resourceRepository.findByDataset(dataset);
        String fileName = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[0];
        String ext = multipartFile.getOriginalFilename().split("\\.")[1];
        if(findByType(ext) == null){
            throw new  FileException("지정되지 않은 파일 형식 입니다");
        }

        //해당 리소스가 존재하면 s3에서 폴더 삭제 후 생성
        if(optionalResource.isPresent()){
            s3StorageManager.deleteFolder(datasetId);
            Resource resource = optionalResource.get();
            FileInfoDto fileInfoDto = s3StorageManager.uploadFile(datasetId, multipartFile);
            resource.updateResource(fileInfoDto.getUrl(),fileInfoDto.getType(),fileName);
        }
        else{
            FileInfoDto fileInfoDto = s3StorageManager.uploadFile(datasetId, multipartFile);
            Resource resource = Resource.builder().
                    resourceUrl(fileInfoDto.getUrl()).
                    type(fileInfoDto.getType()).
                    resourceName(fileName).
                    dataset(dataset).
                    build();

            resourceRepository.save(resource);
        }
    }

    //유저가 다운로드를 하면
    public void download(UserDetails userDetails, Long datasetId){
        User user = userService.findByEmail(userDetails.getUsername());
        Dataset dataset = datasetRepository.findByIdWithTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
        Download download = Download.builder().dataset(dataset).user(user).build();
        downloadRepository.save(download);
    }


    public List<Dataset> popularData(){
        /* 조회수만 기준으로 인기순을 생각하였을 때*/
       // return  datasetRepository.findAll(PageRequest.of(0, 6, Sort.by("views").descending()));


       /* 조회수와, 스크랩수 를 반영하여 인기순을 생각하였을 때
       * 상위 10개 정도 조회수 정렬하여 불러오고
       * 상위 10개 정도 스크랩수 정렬하여 불러와서 비교... 를 하면 안됨. -> 각각 동일한 데이터 셋이라는 보장이 없음.
       * 그렇다면 모두 가져와서 비교...하고, 가중치값을 먹이고 재정렬 -> 자료를 가져오는데 부하가 많지 않을까...?  */
        List<Dataset> datasets =  datasetRepository.findAll();

        // Collections.sort(datasets, new Comparator<Dataset>()
        datasets.sort((d1, d2) -> {
            double score1 = 0.5 * d1.getView() + 0.5 * d1.getDownload();
            double score2 = 0.5 * d2.getView() + 0.5 * d2.getDownload();
            return Double.compare(score2, score1); // 내림차순 정렬
        });

        int start = 0;
        int end = Math.min(6, datasets.size());
        return datasets.subList(start, end); // 굳이 페이지를 사용할 필요는 없음.
    }

    public Page<Dataset> newData(){
        return datasetRepository.findAll(PageRequest.of(0, 6, Sort.by("datasetId").descending()));
    }
}
