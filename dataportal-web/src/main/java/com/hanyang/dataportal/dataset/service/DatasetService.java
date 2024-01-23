package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.ResourceNotFound;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanyang.dataportal.core.dto.ResponseMessage.NOT_EXIST_DATASET;

@Service
@RequiredArgsConstructor
@Transactional
public class DatasetService {
    private final DatasetRepository datasetRepository;
    public Dataset findById(Long id){
        return  datasetRepository.findById(id).orElseThrow(() -> new ResourceNotFound(NOT_EXIST_DATASET));
    }
    public void deleteDataset(Long datasetId){
        datasetRepository.deleteById(datasetId);
    }


}
