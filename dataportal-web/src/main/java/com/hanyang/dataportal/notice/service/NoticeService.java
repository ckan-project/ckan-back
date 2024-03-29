package com.hanyang.dataportal.notice.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.notice.repository.NoticeRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserService userService;

    public Notice create(ReqNoticeDto reqNoticeDto, String email) {
        Notice notice = reqNoticeDto.toEntity();
        User user  = userService.findByEmail(email);
        notice.setAdmin(user);
        return noticeRepository.save(notice);
    }

    public Notice update(ReqNoticeDto reqNoticeDto, Long noticeId) {
        Notice notice =  noticeRepository.findByIdWithAdmin(noticeId).orElseThrow(() -> new ResourceNotFoundException("공지글이 없습니다"));
        notice.updateNotice(reqNoticeDto);
        return notice;
    }
    @Transactional(readOnly = true)
    public Page<Notice> getNoticeList(Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return noticeRepository.findAll(pageable);
    }
    @Transactional
    public Notice getNoticeDetail(Long noticeId) {
        Notice notice = noticeRepository.findByIdWithAdmin(noticeId).orElseThrow(() -> new ResourceNotFoundException("공지글이 없습니다"));
        notice.updateView(notice.getView()+1);
        return notice;
    }

    public void delete(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ResourceNotFoundException("공지글이 없습니다"));
        noticeRepository.delete(notice);
    }
}
