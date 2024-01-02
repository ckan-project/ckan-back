package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.ThemeRepository;
import com.hanyang.dataportal.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository datasetRepository;
    private final ThemeRepository themeRepository;

    public Dataset find(Long id){
        return  datasetRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //datasetList 가져오기
    public List<Dataset> getDatasets(){

        return datasetRepository.findAll();
    }

    //dataset 저장
    //datasetId return
    public Dataset postDataset(ReqDatasetDto reqDatasetDto){

        List<Theme> savedThemes = reqDatasetDto.getThemes()
                .stream()
                .map(Theme::new)
                .map(themeRepository::save)
                .collect(Collectors.toList());

        Dataset dataset = reqDatasetDto.toEntity();
        dataset.setThemes(savedThemes);

        Dataset newDataset = datasetRepository.save(dataset);

        return dataset;
    }

    //dataset 보기
    public Dataset getDatasetDetail(Long datasetId){

        Dataset dataset = find(datasetId);
        dataset.updateView(dataset.getView()+1);

        return dataset;
    }

    //dataset 수정
    public void updateDataset(Long datasetId,ReqDatasetDto reqDatasetDto){

        Dataset dataset = find(datasetId);
        dataset.updateDataset(reqDatasetDto);

    }

    //dataset 삭제
    public void deleteDataset(Long datasetId){
        datasetRepository.deleteById(datasetId);
    }
}
