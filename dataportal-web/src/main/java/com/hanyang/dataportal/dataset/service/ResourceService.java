package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.core.response.ResponseMessage;
import com.hanyang.dataportal.dataset.domain.Resource;
import com.hanyang.dataportal.dataset.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResourceService {
    private final ResourceRepository resourceRepository;
    public Resource findById(Long resourceId){
         return resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.NOT_EXIST_RESOURCE));
    }
}
