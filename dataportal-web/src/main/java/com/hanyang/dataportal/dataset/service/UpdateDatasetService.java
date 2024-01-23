package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateDatasetService {
    private final DatasetRepository datasetRepository;
    private final DatasetService datasetService;
    public Dataset create(ReqDatasetDto reqDatasetDto){
        Dataset dataset = reqDatasetDto.toEntity();
        return datasetRepository.save(dataset);
    }
    public Dataset modify(Long datasetId, ReqDatasetDto reqDatasetDto){
        Dataset dataset = datasetService.findById(datasetId);
        dataset.updateDataset(reqDatasetDto);
        return dataset;
    }
}
