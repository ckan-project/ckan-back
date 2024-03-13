package com.hanyang.dataportal.dataset.service;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final S3StorageManager s3StorageManager;
    private final UserService userService;
    private final DownloadRepository downloadRepository;
    private final DatasetRepository datasetRepository;
    private final ResourceRepository resourceRepository;

    public Resource save(Long datasetId, MultipartFile multipartFile){
        Dataset dataset = datasetRepository.findByIdWithTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));

        Optional<Resource> optionalResource = resourceRepository.findByDataset(dataset);
        String fileName = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[0];

        //해당 리소스가 존재하면 s3에서 폴더 삭제 후 생성
        if(optionalResource.isPresent()){
            s3StorageManager.deleteFolder(datasetId);
            Resource resource = optionalResource.get();
            FileInfoDto fileInfoDto = s3StorageManager.uploadFile(datasetId, multipartFile);
            resource.updateResource(fileInfoDto.getUrl(), fileInfoDto.getType(), fileName);
            return resource;
        }
        else{
            FileInfoDto fileInfoDto = s3StorageManager.uploadFile(datasetId, multipartFile);

            Resource resource = Resource.builder().
                    resourceUrl(fileInfoDto.getUrl()).
                    type(fileInfoDto.getType()).
                    resourceName(fileName).
                    build();

            resource.setDataset(dataset);
            return resourceRepository.save(resource);
        }
    }

    //유저가 다운로드를 하면
    public void download(UserDetails userDetails, Long datasetId){
        User user = userService.findByEmail(userDetails.getUsername());
        Dataset dataset = datasetRepository.findByIdWithTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
        Download download = Download.builder().dataset(dataset).user(user).build();
        downloadRepository.save(download);
    }

}
