package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Notice;
import com.hanyang.dataportal.dataset.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.dataset.repository.NoticeRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoticeServiceTest {
    NoticeRepository noticeRepository;
    UserRepository userRepository;
    NoticeService noticeService;

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

    @Test
    void findNotice() {
        // given

        // when

        // then

    }

    @Test
    void postNotice() {
        // given

        // when

        // then

    }
}