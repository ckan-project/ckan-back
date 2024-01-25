package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.req.ReqScrapDto;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
     * @param userDetails
     * @param reqScrapDto
     * @return
     */
    public Scrap createScrap(UserDetails userDetails, ReqScrapDto reqScrapDto) {
        Dataset dataset = datasetRepository.findById(reqScrapDto.getDatasetId())
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 dataset 정보입니다."));

        User user = userRepository.findByEmailAndActiveTrue(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 정보입니다."));

        scrapRepository.findByDatasetAndUser(dataset, user)
                .ifPresent(scrap -> {
                    throw new EntityExistsException("이미 존재하는 스크랩 내역입니다.");
                });

        Scrap scrap = Scrap.builder()
                .user(user)
                .dataset(dataset)
                .build();

        scrapRepository.save(scrap);
        return scrap;
    }

    /**
     * 유저가 스크랩한 특정 내역을 삭제하는 메서드
     * @param userDetails
     * @param reqScrapDto
     * @return 삭제한 스크랩 객체
     */
    public Scrap removeScrap(UserDetails userDetails, ReqScrapDto reqScrapDto) {
        Dataset dataset = datasetRepository.findById(reqScrapDto.getDatasetId())
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 dataset 정보입니다."));

        User user = userRepository.findByEmailAndActiveTrue(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 정보입니다."));

        Scrap scrap = scrapRepository.findByDatasetAndUser(dataset, user)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스크랩 정보입니다."));

        scrapRepository.deleteById(scrap.getScrapId());

        return scrap;
    }
}
