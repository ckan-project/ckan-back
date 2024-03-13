package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.dto.req.DatasetSearchCond;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.DatasetSearchRepository;
import com.hanyang.dataportal.dataset.repository.DatasetThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DatasetService {
    private final DatasetRepository datasetRepository;
    private final DatasetSearchRepository datasetSearchRepository;
    private final DatasetThemeRepository datasetThemeRepository;

    public Dataset create(ReqDatasetDto reqDatasetDto){
        Dataset dataset = reqDatasetDto.toEntity();

        List<Theme> themeList = reqDatasetDto.getTheme();
        for (Theme theme: themeList) {
            dataset.addTheme(theme);
        }
        return datasetRepository.save(dataset);
    }
    public Dataset modify(Long datasetId, ReqDatasetDto reqDatasetDto){
        Dataset dataset = datasetRepository.findByIdWithTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
        dataset.updateDataset(reqDatasetDto);
        datasetThemeRepository.deleteAll(dataset.getDatasetThemeList());
        dataset.removeTheme();

        List<Theme> themeList = reqDatasetDto.getTheme();
        for (Theme theme: themeList) {
            dataset.addTheme(theme);
        }
        return dataset;
    }

    @Transactional(readOnly = true)
    public Page<Dataset> getDatasetList(DatasetSearchCond datasetSearchCond){
        return datasetSearchRepository.searchDatasetList(datasetSearchCond);

    }
    public Dataset getDatasetDetail(Long datasetId){
        Dataset dataset = datasetRepository.findByIdWithResourceAndTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
        dataset.updateView(dataset.getView()+1);
        return dataset;
    }

    public Dataset findById(Long id){
        return datasetRepository.findByIdWithTheme(id).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
    }
    public void delete(Long id){
        Dataset dataset = findById(id);
        datasetRepository.delete(dataset);
    }

}
