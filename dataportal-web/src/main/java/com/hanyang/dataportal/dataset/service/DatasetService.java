package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.NullException;
import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.domain.vo.Organization;
import com.hanyang.dataportal.dataset.dto.DataSearch;
import com.hanyang.dataportal.dataset.dto.req.ReqDataSearchDto;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.DatasetSearchRepository;
import com.hanyang.dataportal.dataset.repository.DatasetThemeRepository;
import com.hanyang.dataportal.dataset.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DatasetService {
    private final DatasetRepository datasetRepository;
    private final DatasetSearchRepository datasetSearchRepository;
    private final DatasetThemeRepository datasetThemeRepository;
    private final ThemeRepository themeRepository;

    public Dataset create(ReqDatasetDto reqDatasetDto){
        Dataset dataset = reqDatasetDto.toEntity();

        List<Theme> themeList = reqDatasetDto.getTheme().stream()
                .map(value -> themeRepository.findByValue(value)
                        .orElseThrow(() -> new NullException("지정되지 않은 주제 입니다")))
                .toList();

        themeList.forEach(dataset::addTheme);

        return datasetRepository.save(dataset);
    }
    public Dataset modify(Long datasetId, ReqDatasetDto reqDatasetDto){
        Dataset dataset = datasetRepository.findByIdWithTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
        dataset.updateDataset(reqDatasetDto);
        datasetThemeRepository.deleteAll(dataset.getDatasetThemeList());
        dataset.removeTheme();

        List<Theme> themeList = reqDatasetDto.getTheme().stream()
                .map(value -> themeRepository.findByValue(value)
                        .orElseThrow(() -> new NullException("지정되지 않은 주제 입니다")))
                .toList();

        themeList.forEach(dataset::addTheme);
        return dataset;
    }

    @Transactional(readOnly = true)
    public Page<Dataset> getDatasetList(ReqDataSearchDto reqDataSearchDto){

        List<Theme> themeList = Optional.ofNullable(reqDataSearchDto.getTheme())
                .map(themes -> themes.stream()
                        .map(value -> themeRepository.findByValue(value)
                                .orElseThrow(() -> new NullException("지정되지 않은 주제입니다")))
                        .toList())
                .orElse(Collections.emptyList());


        List<Organization> organizationList = Optional.ofNullable(reqDataSearchDto.getOrganization())
                .map(organizations -> organizations.stream()
                        .map(Organization::new)
                        .toList())
                .orElse(Collections.emptyList());

        DataSearch dataSearch = DataSearch.builder().keyword(reqDataSearchDto.getKeyword()).
                organization(organizationList).
                theme(themeList).
                page(reqDataSearchDto.getPage()).
                type(reqDataSearchDto.getType()).
                sort(reqDataSearchDto.getSort()).
                build();
        return datasetSearchRepository.searchDatasetList(dataSearch);

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
