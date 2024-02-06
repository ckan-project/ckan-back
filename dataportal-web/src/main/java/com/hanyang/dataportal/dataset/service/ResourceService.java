package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.core.response.ResponseMessage;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Resource;
import com.hanyang.dataportal.dataset.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResourceService {
    private final DatasetService datasetService;
    private final ResourceRepository resourceRepository;
    private final S3Service s3Service;

    public Resource findById(Long resourceId){
         return resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.NOT_EXIST_RESOURCE));
    }

    public Resource save(Long datasetId, MultipartFile multipartFile) throws IOException {
        Dataset dataset = datasetService.findById(datasetId);

        Optional<Resource> optionalDataset = resourceRepository.findByDataset(dataset);

        if(optionalDataset.isPresent()){
            Resource resource = optionalDataset.get();
            Resource newResource = s3Service.uploadFile(datasetId, multipartFile);
            resource.updateResource(newResource.getResourceUrl(), newResource.getType(),newResource.getResourceName());
            return resource;
        }
        else{
            s3Service.deleteFolder(datasetId);
            Resource resource = s3Service.uploadFile(datasetId, multipartFile);
            resource.setDataset(dataset);
            return resourceRepository.save(resource);
        }
    }
}
