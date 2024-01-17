package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.dataset.domain.Notice;
import com.hanyang.dataportal.dataset.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.dataset.repository.NoticeRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    /**
     * 등록된 공지사항 리스트 가져오기
     * @return Notice 객체 리스트
     */
    public List<Notice> getNotices() {
        return noticeRepository.findAll();
    }

    /**
     * id 값에 해당하는 공지사항 가져오기
     * @param noticeId
     * @return Notice 객체
     */
    public Notice findNotice(Long noticeId) {
        return noticeRepository.findByNoticeId(noticeId)
                .orElseThrow(() -> new EntityNotFoundException("잘못된 notice id값 입니다."));
    }

    /**
     * 해당하는 공지사항을 새로 생성하기
     * @param reqNoticeDto
     * @param userId
     * @return 생성된 Notice 객체
     */
    public Notice postNotice(ReqNoticeDto reqNoticeDto, Long userId) {
        Notice notice = new Notice();
        notice.setTitle(reqNoticeDto.getTitle());
        notice.setContent(reqNoticeDto.getContent());

        notice.setView(0);
        notice.setCreateDate(LocalDate.now());
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("잘못된 user id 값입니다."));
        notice.setUser(user);

        noticeRepository.save(notice);

        return notice;
    }
}
