package com.hanyang.dataportal.notice.service;


import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "공지사항 ~ 리스트조회")
public class NoticeListService {

    private final NoticeRepository noticeRepository;

    public NoticeListService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> findAllNotice() {
    return noticeRepository.findAll();

}

}
