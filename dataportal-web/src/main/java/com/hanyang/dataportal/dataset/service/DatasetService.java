package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.core.response.ResponseMessage;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Resource;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanyang.dataportal.core.response.ResponseMessage.NOT_EXIST_DATASET;

@Service
@RequiredArgsConstructor
@Transactional
public class DatasetService {
    private final DatasetRepository datasetRepository;
    private final ResourceService resourceService;
    public Dataset findById(Long id){
        return  datasetRepository.findByIdWithTheme(id).orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_DATASET));
    }
    public void deleteDataset(Long datasetId){
        datasetRepository.deleteById(datasetId);
    }

    public Dataset findByResourceId(Long resourceId){
        Resource resource = resourceService.findById(resourceId);
        return datasetRepository.findByResource(resource).orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.NOT_EXIST_DATASET));
    }

}
