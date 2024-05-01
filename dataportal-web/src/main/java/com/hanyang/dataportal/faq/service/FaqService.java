package com.hanyang.dataportal.faq.service;


import com.hanyang.dataportal.faq.domain.Faq;
import com.hanyang.dataportal.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor

public class FaqService {
    private final FaqRepository faqRepository;


    @Transactional(readOnly = true)
    public Page<Faq> getFaqList(Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return faqRepository.findAll(pageable);
    }
}
