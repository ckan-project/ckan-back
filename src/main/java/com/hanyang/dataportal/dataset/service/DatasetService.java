package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.exception.CustomException.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository datasetRepository;


    public Dataset find(Long id){
        return  datasetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 Dataset은 존재하지 않습니다."));
    }

    //datasetList 가져오기
    public List<Dataset> getDatasets(){

        return datasetRepository.findAll();
    }

    //dataset 저장
    //datasetId return
    public void postDataset(ReqDatasetDto reqDatasetDto){
        Dataset dataset = reqDatasetDto.toEntity();
        datasetRepository.save(dataset);

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
