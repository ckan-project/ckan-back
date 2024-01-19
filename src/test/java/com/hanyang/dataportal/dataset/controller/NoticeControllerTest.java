package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.dataset.domain.Notice;
import com.hanyang.dataportal.dataset.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.dataset.repository.NoticeRepository;
import com.hanyang.dataportal.dataset.service.NoticeService;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import com.hanyang.dataportal.utill.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NoticeControllerTest {
    private NoticeController noticeController;
    private NoticeService noticeService;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        noticeService = new NoticeService(noticeRepository, userRepository);
        noticeController = new NoticeController(noticeService);
    }

    @Test
    void getNoticeList() {
    }

    @DisplayName("GET notice 정상 및 예외 케이스 테스트")
    @Test
    void getNotice() {
        // given
        User user = new User();
        userRepository.save(user);

        ReqNoticeDto reqNoticeDto = new ReqNoticeDto();
        Notice notice = noticeService.postNotice(reqNoticeDto, user.getUserId());

        // when
        ApiResponse<?> successResponse = noticeController.getNotice(notice.getNoticeId());
        ApiResponse<?> errorResponse = noticeController.getNotice(notice.getNoticeId() + 1);

        // then
        Assertions.assertThat(successResponse.isSuccess()).isTrue();
        Assertions.assertThat(errorResponse.getStatus()).isEqualTo(404);
    }

    @DisplayName("POST notice 정상 및 예외 케이스 테스트")
    @Test
    void postNotice() {
        // given
        User user = new User();
        userRepository.save(user);

        ReqNoticeDto reqNoticeDto = new ReqNoticeDto();

        // when
        ApiResponse<?> successResponse = noticeController.postNotice(user.getUserId(), reqNoticeDto);
        ApiResponse<?> errorResponse = noticeController.postNotice(user.getUserId() + 1, reqNoticeDto);

        // then
        Assertions.assertThat(successResponse.isSuccess()).isTrue();
        Assertions.assertThat(errorResponse.getStatus()).isEqualTo(404);
    }
}