package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.dto.ResponseMessage;
import com.hanyang.dataportal.core.exception.ResourceExistException;
import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;

    /**
     * 유저가 스크랩한 모든 Scrap 객체를 가져오는 메서드
     * @param email
     * @return
     */
    public List<Scrap> findAllByEmail(String email) {
        return scrapRepository.findAllByUserEmail(email);
    }

    /**
     * 하나의 Scrap 객체를 가져오는 메서드
     * @param scrapId
     * @return
     */
    public Scrap findByScrapId(Long scrapId) {
        return scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.NOT_EXIST_SCRAP));
    }

    /**
     * dataset과 user로 스크랩 객체를 찾는 메서드
     * @param dataset
     * @param user
     * @return
     */
    public Scrap findByDatasetAndUser(Dataset dataset, User user) {
        return scrapRepository.findByDatasetAndUser(dataset, user)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.NOT_EXIST_SCRAP));
    }

    /**
     * dataset과 user로 중복된 스크랩 객체가 있는지 확인하는 메서드
     * @param dataset
     * @param user
     */
    public void checkDuplicateByDatasetAndUser(Dataset dataset, User user) {
        scrapRepository.findByDatasetAndUser(dataset, user)
                .ifPresent(scrap -> {
                    throw new ResourceExistException(ResponseMessage.DUPLICATE_SCRAP);
                });
    }
}
