package com.hanyang.dataportal.notice.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.notice.repository.NoticeRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Sql("/h2-truncate.sql")
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("공지글을 작성할 수 있다.")
    void create() {
        //Given
        ReqNoticeDto reqNoticeDto = new ReqNoticeDto();
        reqNoticeDto.setTitle("테스트");
        User user = userRepository.save(User.builder().email("test@email.com").build());

        //When
        Notice notice = noticeService.create(reqNoticeDto, user.getEmail());

        //Then
        Assertions.assertThat(noticeRepository.findById(notice.getNoticeId())).isPresent();
        Assertions.assertThat(notice.getAdmin().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("공지글을 수정할 수 있다.")
    void update() {
        //Given
        ReqNoticeDto reqNoticeDto = new ReqNoticeDto();
        reqNoticeDto.setTitle("변경 후");
        User user = userRepository.save(User.builder().email("test@email.com").build());
        Notice notice = noticeRepository.save(Notice.builder().title("변경 전").admin(user).build());

        //When
        Notice getNotice = noticeService.update(reqNoticeDto, notice.getNoticeId());

        //Then
        Assertions.assertThat(getNotice.getTitle()).isEqualTo("변경 후");
    }

    @Test
    @DisplayName("10개씩 페이징해서 공지글을 가져올 수 있다.")
    void getNoticeList() {
        //Given
        int page = 0;
        for (int i = 0; i < 11; i++) {
            noticeRepository.save(Notice.builder().build());
        }

        //When
        Page<Notice> getNotice = noticeService.getNoticeList(page);

        //Then
        Assertions.assertThat(getNotice.getTotalPages()).isEqualTo(2);
    }

    @Test
    @DisplayName("공지글을 조회하면 조회수가 1 올라간다.")
    void getNoticeDetail() {
        //Given
        User user = userRepository.save(User.builder().email("test@email.com").build());
        Notice notice = noticeRepository.save(Notice.builder().view(0).admin(user).build());

        //When
        Notice getNotice = noticeService.getNoticeDetail(notice.getNoticeId());

        //Then
        Assertions.assertThat(getNotice.getView()).isEqualTo(notice.getView()+1);
    }

    @Test
    @DisplayName("존재하지 않는 공지글은 볼 수 없다.")
    void getNoticeDetailError() {
        //Given
        Long noticeId = 1L;

        //When & Then
        Assertions.assertThatThrownBy(()->{
                    noticeService.getNoticeDetail(noticeId);})
                .isExactlyInstanceOf(ResourceNotFoundException.class).hasMessage("공지글이 없습니다");
    }

    @Test
    @DisplayName("공지글을 삭제할 수 있다.")
    void delete() {
        //Given
        Notice notice = noticeRepository.save(Notice.builder().build());

        //When
        noticeService.delete(notice.getNoticeId());

        //Then
        Assertions.assertThat(noticeRepository.findById(notice.getNoticeId())).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 공지글은 삭제할 수 없다.")
    void deleteError() {
        //Given
        long datasetId = 1L;

        //When & Then
        Assertions.assertThatThrownBy(()->{
                    noticeService.delete(datasetId);})
                .isExactlyInstanceOf(ResourceNotFoundException.class).hasMessage("공지글이 없습니다");
    }
}