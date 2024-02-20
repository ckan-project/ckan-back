package com.hanyang.dataportal.qna.dto.req;

import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Tag(name = "질문하기 API")
public class ReqQuestionDto {

    @NotBlank
    private static String title;
    @NotBlank
    private static String content;
    private static User user;

    public static Question toEntity() {
        return Question.builder().
                title(title).
                content(content).
                date(LocalDate.now()).
                view(0).
                answerStatus(AnswerStatus.valueOf("Waiting")).
                user(user).
                build();
    }

}


//public class Question {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long questionId;
//    private String title;
//    @Lob
//    private String content;
//    private LocalDate date;
//    private Integer view;
//    @Enumerated(EnumType.STRING)
//    private AnswerStatus answerStatus;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
//}