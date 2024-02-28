package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.DatasetThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateDatasetService {
    private final DatasetRepository datasetRepository;
    private final DatasetThemeRepository datasetThemeRepository;
    private final DatasetService datasetService;
    public Dataset create(ReqDatasetDto reqDatasetDto){
        Dataset dataset = reqDatasetDto.toEntity();

        List<Theme> themeList = reqDatasetDto.getTheme();
        for (Theme theme: themeList) {
             dataset.addTheme(theme);
        }
        return datasetRepository.save(dataset);
    }
    public Dataset modify(Long datasetId, ReqDatasetDto reqDatasetDto){
        Dataset dataset = datasetService.findById(datasetId);
        dataset.updateDataset(reqDatasetDto);
        datasetThemeRepository.deleteAll(dataset.getDatasetThemeList());
        dataset.removeTheme();

        List<Theme> themeList = reqDatasetDto.getTheme();
        for (Theme theme: themeList) {
            dataset.addTheme(theme);
        }


        return dataset;
    }
}
