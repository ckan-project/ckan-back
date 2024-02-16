package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.dto.ResponseMessage;
import com.hanyang.dataportal.core.exception.ResourceExist;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.service.DatasetService;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateScrapServiceTest {
    @InjectMocks
    private UpdateScrapService updateScrapService;

    @Mock
    private ScrapService scrapService;
    @Mock
    private ScrapRepository scrapRepository;
    @Mock
    private UserService userService;
    @Mock
    private DatasetService datasetService;

    @Mock
    private UserDetails userDetails;

    @Nested
    @DisplayName("스크랩 생성")
    class CreateScrap {
        private Long datasetId;
        private String userEmail;

        @BeforeEach
        void setup() {
            datasetId = 1L;
            userEmail = "test";
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("새로운 스크랩 객체 생성")
            void createScrapSuccess() {
                // given
                Dataset dataset = Dataset.builder()
                        .datasetId(datasetId)
                        .build();
                User user = User.builder()
                        .email(userEmail)
                        .build();

                when(datasetService.findById(datasetId)).thenReturn(dataset);
                when(userService.findByEmail(userEmail)).thenReturn(user);
                when(userDetails.getUsername()).thenReturn(userEmail);

                // when
                Scrap scrap = updateScrapService.createScrap(userDetails, dataset.getDatasetId());

                // then
                Assertions.assertThat(scrap.getDataset().getDatasetId())
                        .isEqualTo(datasetId);
                Assertions.assertThat(scrap.getUser().getEmail())
                        .isEqualTo(userEmail);
            }
        }

        @Nested
        @DisplayName("예외 케이스")
        class FailCase {
            @Test
            @DisplayName("중복 스크랩 생성")
            void createScrapFail() {
                // given
                Dataset dataset = Dataset.builder()
                        .datasetId(datasetId)
                        .build();
                User user = User.builder()
                        .email(userEmail)
                        .build();

                when(datasetService.findById(datasetId)).thenReturn(dataset);
                when(userDetails.getUsername()).thenReturn(userEmail);
                when(userService.findByEmail(userEmail)).thenReturn(user);
                doThrow(new ResourceExist(ResponseMessage.DUPLICATE_SCRAP))
                        .when(scrapService).checkDuplicateByDatasetAndUser(dataset, user);

                // when
                Exception exception = assertThrows(ResourceExist.class, () -> {
                    updateScrapService.createScrap(userDetails, datasetId); // fail
                });

                // then
                Assertions.assertThat(exception.getMessage())
                        .isEqualTo(ResponseMessage.DUPLICATE_SCRAP);
            }
        }
    }

    @Nested
    @DisplayName("스크랩 삭제")
    class DeleteScrap {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            void removeScrapSuccess1() {}
        }

        @Nested
        @DisplayName("예외 케이스")
        class FailCase {
            @Test
            void removeScrapFail1() {}
        }
    }
}