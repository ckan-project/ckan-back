package com.hanyang.dataportal.faq.repository;

import com.hanyang.dataportal.faq.domain.Faq;
import com.hanyang.dataportal.faq.domain.FaqCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
    Page<Faq> findAll(Pageable pageable);
    Page<Faq> findByFaqCategory(FaqCategory faqCategory, Pageable pageable);

}

