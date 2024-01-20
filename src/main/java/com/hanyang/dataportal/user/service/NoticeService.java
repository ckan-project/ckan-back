package com.hanyang.dataportal.user.service;


import com.hanyang.dataportal.user.domain.Notice;
import com.hanyang.dataportal.user.dto.NoticeDTO;
import com.hanyang.dataportal.user.dto.NoticeResponseDTO;
import com.hanyang.dataportal.user.repository.NoticeRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    //dataSetSerice

    public void save(NoticeDTO noticeDTO) {
        Notice notice = noticeDTO.toNoticeEntity();
        notice.setView(0);
        notice.setUser(userRepository.findByUserId(noticeDTO.getUserId()));
        noticeRepository.save(notice);

        //noticeid(auto),user, date, view,
        // userid를 통해서 user 객체를 만든다..
        // -> userid의 정보는 ... noticeDTO..
        // user의 객체..? 데이터베이스에 있는 정보를..
    }

    //DTO는 get으로 들어오지 않을 수 있다고?
    //id를 경로값이든 아이디를 받는다
    // dTO는 없고, notice Id
    // NoticeRepository. findbyid~ -> notity entity
    // notice의 정보를 빼어 DTO로 만들어서 내려준다
    //notice에서 정보를 뺴러 noticeRes~DTO에 생성자를 통해
    //optional 의 개념 모름
    //
    public Notice noticeResponseDTO (Long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        Notice notice1 = notice.get();
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.toNoticeResponseDTO(notice1);
        return noticeResponseDTO;
    }
}
