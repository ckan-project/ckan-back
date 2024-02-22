package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.DatasetSearchCond;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.DatasetSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanyang.dataportal.core.response.ResponseMessage.NOT_EXIST_DATASET;

@Service
@RequiredArgsConstructor
@Transactional
public class ReadDatasetService {

    private final DatasetRepository datasetRepository;
    private final DatasetSearchRepository datasetSearchRepository;

    @Transactional(readOnly = true)
    public Page<Dataset> getDatasetList(DatasetSearchCond datasetSearchCond){
        return datasetSearchRepository.searchDatasetList(datasetSearchCond);
    }

    public Dataset getDatasetDetail(Long datasetId){
        Dataset dataset = datasetRepository.findByIdWithResourceAndTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_DATASET));
        dataset.updateView(dataset.getView()+1);
        return dataset;
    }
}
