package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Resource;
import com.hanyang.dataportal.dataset.dto.req.ReqResourceDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.ResourceRepository;
import com.hanyang.dataportal.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final DatasetRepository datasetRepository;

    public void createResource(Long datasetId,ReqResourceDto reqResourceDto){

        Resource resource = reqResourceDto.toEntity();

        Dataset dataset = datasetRepository.findById(datasetId).orElseThrow(EntityNotFoundException::new);

        resource.setDataset(dataset);

        resourceRepository.save(resource);
    }

}
