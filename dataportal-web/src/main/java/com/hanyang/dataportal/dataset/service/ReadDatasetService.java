package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetListDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadDatasetService {

    private final DatasetService datasetService;
    private final DatasetRepository datasetRepository;

    public ResDatasetListDto getDatasetList(String theme, String organization, String keyword, String sort, int page){
        Sort dynamicSort = Sort.by(sort);
        Pageable pageable = PageRequest.of(page,10,dynamicSort);
        Page<Dataset> datasetList = datasetRepository.findByTitleContainingAndThemeAndOrganization(keyword, theme, organization, pageable);


        datasetList.getTotalElements();
        List<Dataset> content = datasetList.getContent();

        return new ResDatasetListDto(datasetList.getTotalPages(),datasetList.getTotalElements(),content);
    }

    public Dataset getDatasetDetail(Long datasetId){
        Dataset dataset = datasetService.findById(datasetId);
        //조회수 업데이트
        dataset.updateView(dataset.getView()+1);
        return dataset;
    }
}
