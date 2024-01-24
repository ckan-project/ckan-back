package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.CustomUserDetails;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final DatasetRepository datasetRepository;

    /**
     * 유저가 스크랩한 모든 Scrap 객체를 가져오는 메서드
     * @param email
     * @return
     */
    public List<Scrap> findAllByEmail(String email) {
        User user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 정보입니다."));

        return scrapRepository.findAllByUser(user);
    }

    /**
     * 하나의 Scrap 객체를 가져오는 메서드
     * @param scrapId
     * @return
     */
    public Scrap findByScrapId(Long scrapId) {
        return scrapRepository.findById(scrapId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스크랩 정보입니다."));
    }

    /**
     * 새로운 Scrap 객체를 생성하는 메서드
     * @param customUserDetails
     * @param datasetId
     * @return
     */
    public Scrap createScrap(CustomUserDetails customUserDetails, Long datasetId) {
        User user = userRepository.findByEmailAndActiveTrue(customUserDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 정보입니다."));

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 dataset 정보입니다."));

        Scrap scrap = Scrap.builder()
                .user(user)
                .dataset(dataset)
                .build();

        scrapRepository.save(scrap);
        return scrap;
    }
}
