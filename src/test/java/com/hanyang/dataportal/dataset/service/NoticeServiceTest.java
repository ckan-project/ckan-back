package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Notice;
import com.hanyang.dataportal.dataset.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.dataset.repository.NoticeRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NoticeServiceTest {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;
    private NoticeService noticeService;

    @BeforeEach
    public void beforeEach() {
        noticeService = new NoticeService(noticeRepository, userRepository);
    }

    @AfterEach
    public void afterEach() {
        noticeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getNotices() {
        // given

        // when

        // then

    }

    @DisplayName("잘못된 notice id 예외")
    @Test
    void findNotice_id값_예외() {
        // given
        User user = new User();
        userRepository.save(user);

        ReqNoticeDto reqNoticeDto = new ReqNoticeDto();
        Notice notice = noticeService.postNotice(reqNoticeDto, user.getUserId());

        // when
        noticeService.findNotice(notice.getNoticeId());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> noticeService.findNotice(notice.getNoticeId() + 1));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("잘못된 notice id값 입니다.");
    }

    @DisplayName("잘못된 user id 예외")
    @Test
    void postNotice_userId_예외() {
        // given
        User user = new User();
        userRepository.save(user);
        ReqNoticeDto reqNoticeDto = new ReqNoticeDto();

        // when
        noticeService.postNotice(reqNoticeDto, user.getUserId());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> noticeService.postNotice(reqNoticeDto, user.getUserId() + 1));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("잘못된 user id 값입니다.");
    }
}