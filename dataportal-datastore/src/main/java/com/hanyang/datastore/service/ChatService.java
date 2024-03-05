package com.hanyang.datastore.service;

import com.hanyang.datastore.repository.CustomContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final CustomContentRepository customContentRepository;

    public String findByKeyword(String keyword){
        return customContentRepository.searchContent(keyword);
    }
}
