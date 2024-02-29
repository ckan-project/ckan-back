package com.hanyang.dataportal.notice.service;


import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.repository.NoticeRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service (value = "공지사항 ~ 단건조회")
public class NoticeDetailService {
    private final NoticeRepository noticeRepository;
    private final UserService userService;
    NoticeDetailService(NoticeRepository noticeRepository,UserService userService) {
        this.noticeRepository = noticeRepository;
        this.userService =  userService;
    }

    // 1. Notice Create
    public Notice createNotice(Notice notice, String email) {
        User user  = userService.findByEmail(email);
        notice.setAdmin(user);

        return noticeRepository.save(notice);
    }
    public Notice findNotice(Long noticeId){
        return noticeRepository.findNoticeByNoticeId(noticeId)
                .orElseThrow(() -> new ResourceNotFoundException("공지글이 없음"));
    }

    public Optional<Notice> updateNotice(Notice notice, Long noticeId) {
       // noticeId를 통해 username을 찾아와서,
        // notice에 넣어준다...
        Notice getNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new ResourceNotFoundException("공지글의 없읍니다. "));

        // ResNoticeDto noticeEntity = Notice.toNoticeUpdateDto(getNotice);
        notice.setAdmin(getNotice.getAdmin());
       // notice.setTitle(getNotice.getTitle());
       // notice.setContent(getNotice.getContent());
        notice.setNoticeId(getNotice.getNoticeId());
        // Notie 게시글의 존재검증
       // if(noticeRepository.existsById(noticeId)){
        // Notice notice = new Notice();
             noticeRepository.save(notice);
        return Optional.of(getNotice);
      //  } else  return null;
    }

    public boolean deleteNotice(Long noticeId) {
    if(noticeRepository.existsById(noticeId)) {
        noticeRepository.deleteById(noticeId);
        return true;
    } else return false;
    }



}
