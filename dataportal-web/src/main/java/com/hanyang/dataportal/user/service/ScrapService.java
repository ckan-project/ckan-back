package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.dto.ResponseMessage;
import com.hanyang.dataportal.core.exception.ResourceExist;
import com.hanyang.dataportal.core.exception.ResourceNotFound;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.service.DatasetService;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserService userService;
    private final DatasetService datasetService;

    /**
     * 유저가 스크랩한 모든 Scrap 객체를 가져오는 메서드
     * @param email
     * @return
     */
    public List<Scrap> findAllByEmail(String email) {
        User user = userService.findByEmail(email);
        return scrapRepository.findAllByUser(user);
    }

    /**
     * 하나의 Scrap 객체를 가져오는 메서드
     * @param scrapId
     * @return
     */
    public Scrap findByScrapId(Long scrapId) {
        return scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ResourceNotFound(ResponseMessage.NOT_EXIST_SCRAP));
    }

    /**
     * dataset과 user로 스크랩 객체를 찾는 메서드
     * @param dataset
     * @param user
     * @return
     */
    public Scrap findByDatasetAndUser(Dataset dataset, User user) {
        return scrapRepository.findByDatasetAndUser(dataset, user)
                .orElseThrow(() -> new ResourceNotFound(ResponseMessage.NOT_EXIST_SCRAP));
    }

    /**
     * 새로운 Scrap 객체를 생성하는 메서드
     * @param userDetails
     * @param datasetId
     * @return
     */
    public Scrap createScrap(UserDetails userDetails, Long datasetId) {
        Dataset dataset = datasetService.findById(datasetId);
        User user = userService.findByEmail(userDetails.getUsername());

        scrapRepository.findByDatasetAndUser(dataset, user)
                .ifPresent(scrap -> {
                    throw new ResourceExist(ResponseMessage.DUPLICATE_SCRAP);
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
     * @param datasetId
     * @return
     */
    public Scrap removeScrap(UserDetails userDetails, Long datasetId) {
        Dataset dataset = datasetService.findById(datasetId);
        User user = userService.findByEmail(userDetails.getUsername());
        Scrap scrap = findByDatasetAndUser(dataset, user);

        scrapRepository.deleteById(scrap.getScrapId());

        return scrap;
    }
}
