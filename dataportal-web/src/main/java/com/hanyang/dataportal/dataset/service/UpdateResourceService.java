package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Resource;
import com.hanyang.dataportal.dataset.repository.DownloadRepository;
import com.hanyang.dataportal.dataset.repository.ResourceRepository;
import com.hanyang.dataportal.user.domain.Download;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateResourceService {

    private final DatasetService datasetService;
    private final ResourceRepository resourceRepository;
    private final S3Service s3Service;
    private final UserService userService;
    private final DownloadRepository downloadRepository;

    public Resource save(Long datasetId, MultipartFile multipartFile){
        Dataset dataset = datasetService.findById(datasetId);

        Optional<Resource> optionalDataset = resourceRepository.findByDataset(dataset);

        if(optionalDataset.isPresent()){
            s3Service.deleteFolder(datasetId);
            Resource resource = optionalDataset.get();
            Resource newResource = s3Service.uploadFile(datasetId, multipartFile);
            resource.updateResource(newResource.getResourceUrl(), newResource.getType(),newResource.getResourceName());
            return resource;
        }
        else{
            Resource resource = s3Service.uploadFile(datasetId, multipartFile);
            resource.setDataset(dataset);
            return resourceRepository.save(resource);
        }
    }

    public void download(UserDetails userDetails, Long datasetId){
        User user = userService.findByEmail(userDetails.getUsername());
        Dataset dataset = datasetService.findById(datasetId);
        Download download = Download.builder().dataset(dataset).user(user).build();
        downloadRepository.save(download);
    }
}
