package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final DatasetService datasetService;

    /**
     * s3에 넣고 DB에 해당 데이터에 맞게 업로드
     * 비동기로 datastore에 넘김
     */
    public void uploadResource(Long datasetId){
        Dataset dataset = datasetService.findById(datasetId);
    }
}
