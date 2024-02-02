package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ScrapServiceTest {
    @InjectMocks
    private ScrapService scrapService;

    @Mock
    UserRepository userRepository;
    @Mock
    ScrapRepository scrapRepository;
    @Mock
    DatasetRepository datasetRepository;
    @Mock
    CustomUserDetailsService customUserDetailsService;

    @Nested
    @DisplayName("스크랩 생성 테스트")
    class CreateScrap { }

    @Test
    void findAllByEmail() {
    }

    @Test
    void findByScrapId() {
    }

    @Test
    void createScrap() {
    }

    @Test
    void removeScrap() {
    }
}