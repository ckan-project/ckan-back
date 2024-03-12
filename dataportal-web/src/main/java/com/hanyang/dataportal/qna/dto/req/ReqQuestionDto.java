package com.hanyang.dataportal.qna.dto.req;


import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReqQuestionDto {
    @NotBlank
    private String title;
    private String content;

    // 현재 유저 정보 가져오기, 동작하는지는 확실하지 않음.. 이러면 된다 카더라..
    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // 실제 User 객체로 currentUser에 담음..
    //  User user = (User) authentication.getPrincipal();

   //  private int page;

    public Question toEntity() {
        return Question.builder()
                .title(title)
                .content(content)
                .date(LocalDate.now())
                .view(0)
                .answerStatus(AnswerStatus.Waiting)
                .build();
    }


    public Question toUpdateEntity() {
        return Question.builder()
                .title(title)
                .content(content)
                .build();
    }
}
