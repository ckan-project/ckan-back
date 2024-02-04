package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.dto.ResponseMessage;
import com.hanyang.dataportal.core.exception.ResourceExist;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScrapServiceTest {
    @InjectMocks
    private ScrapService scrapService;

    @Mock
    ScrapRepository scrapRepository;

    private static Long datasetId;
    private static String userEmail;

    @BeforeAll
    static void setup() {
        datasetId = 1L;
        userEmail = "test";
    }

    @Nested
    @DisplayName("예외 케이스")
    class FailCase {
        @Test
        @DisplayName("중복 스크랩 존재 여부 확인하기")
        void checkDuplicateByDatasetAndUser() {
            // given
            Dataset dataset = Dataset.builder()
                    .datasetId(datasetId)
                    .build();
            User user = User.builder()
                    .email(userEmail)
                    .build();
            Scrap scrap = Scrap.builder()
                    .user(user)
                    .dataset(dataset)
                    .build();

            when(scrapRepository.findByDatasetAndUser(dataset, user))
                    .thenReturn(Optional.of(scrap));

            // when
            Exception exception = assertThrows(ResourceExist.class, () -> {
                scrapService.checkDuplicateByDatasetAndUser(dataset, user);
            });

            // then
            Assertions.assertThat(exception.getMessage())
                    .isEqualTo(ResponseMessage.DUPLICATE_SCRAP);
        }
    }
}