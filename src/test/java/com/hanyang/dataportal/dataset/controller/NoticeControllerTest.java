package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.dataset.repository.NoticeRepository;
import com.hanyang.dataportal.dataset.service.NoticeService;
import com.hanyang.dataportal.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoticeControllerTest {
    NoticeController noticeController;
    NoticeService noticeService;
    NoticeRepository noticeRepository;
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        noticeService = new NoticeService(noticeRepository, userRepository);
        noticeController = new NoticeController(noticeService);
    }

    @Test
    void getNoticeList() {
    }

    @Test
    void getNotice() {
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