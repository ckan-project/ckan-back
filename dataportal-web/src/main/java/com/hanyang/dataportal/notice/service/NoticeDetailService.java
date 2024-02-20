package com.hanyang.dataportal.notice.service;


import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.dto.req.ReqUpdateNoticeDto;
import com.hanyang.dataportal.notice.repository.NoticeRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service (value = "공지사항 ~ 단건조회")
public class NoticeDetailService {
    private final NoticeRepository noticeRepository;
    private final UserService userService;
    NoticeDetailService(NoticeRepository noticeRepository,  UserService userService) {
        this.noticeRepository = noticeRepository;
        this.userService =  userService;
    }


    // 1. Notice Create
    public Notice createNotice(Notice reqNoticeDto, String email) {
        //회원정보의 존재의 검증
        User user  = userService.findByEmail(email);

        //Dto에서 넘겨받은 정보를 Notice 타입의 notice 변수에 저장.
        Notice notice = new Notice();
        notice.setTitle(reqNoticeDto.getTitle());
        notice.setContent(reqNoticeDto.getContent());
        notice.setCreateDate(LocalDate.now());
        notice.setview(0);
        notice.setUser(user);
        // noticeRepository에 save.

        return  noticeRepository.save(notice);
    }
    public Notice findNotice(Long noticeId){
        return noticeRepository.findNoticeByNoticeId(noticeId)
                .orElseThrow(() -> new ResourceNotFoundException("공지글이 없음"));
    }

    public Notice updateNotice(ReqUpdateNoticeDto reqUpdateNoticeDto, Long noticeId) {
        // Notie 게시글의 존재검증
        if(noticeRepository.existsById(noticeId)){
        Notice notice = new Notice();
        notice.setTitle(reqUpdateNoticeDto.getTitle());
        notice.setContent(reqUpdateNoticeDto.getContent());
        notice.setUpdateDateTime(LocalDate.now());
            return noticeRepository.save(notice);
        } else  return null;
    }

    public boolean deleteNotice(Long noticeId) {
    if(noticeRepository.existsById(noticeId)) {
        noticeRepository.deleteById(noticeId);
        return true;
    } else return false;
    }



}
