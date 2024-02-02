package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.service.DatasetService;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateScrapService {
    private final ScrapService scrapService;
    private final ScrapRepository scrapRepository;
    private final UserService userService;
    private final DatasetService datasetService;

    /**
     * 새로운 Scrap 객체를 생성하는 메서드
     * @param userDetails
     * @param datasetId
     * @return
     */
    public Scrap createScrap(UserDetails userDetails, Long datasetId) {
        Dataset dataset = datasetService.findById(datasetId);
        User user = userService.findByEmail(userDetails.getUsername());
        scrapService.checkDuplicateByDatasetAndUser(dataset, user);

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
        Scrap scrap = scrapService.findByDatasetAndUser(dataset, user);

        scrapRepository.deleteById(scrap.getScrapId());

        return scrap;
    }
}
