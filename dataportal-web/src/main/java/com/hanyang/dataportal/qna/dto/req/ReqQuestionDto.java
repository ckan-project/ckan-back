package com.hanyang.dataportal.qna.dto.req;


import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReqQuestionDto {
    @NotBlank
    private String title;
    private String content;

    // 현재 유저 정보 가져오기, 동작하는지는 확실하지 않음.. 이러면 된다 카더라..
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // 실제 User 객체로 currentUser에 담음..
    User user = (User) authentication.getPrincipal();
    @Getter
    private int page;

    public Question toEntity() {
        return Question.builder()
                .title(title)
                .content(content)
                .date(LocalDate.now())
                .view(0)
                .answerStatus(AnswerStatus.valueOf("waiting"))
                .user(user) // ~ 현재의 유저로 저장하기(로그인한 유저 부분)
                .build();
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Question toUpdateEntity() {
        return Question.builder()
                .title(title)
                .content(content)
                .build();
    }
}
