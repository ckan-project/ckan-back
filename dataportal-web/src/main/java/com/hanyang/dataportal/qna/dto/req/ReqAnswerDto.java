package com.hanyang.dataportal.qna.dto.req;

import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Tag(name = "답변하기 API")
public class ReqAnswerDto {

    @NotBlank
    private static Long answerId;
    @NotBlank
    private static String content;
    private static LocalDate date;
    private static Question question;
    private static User admin;

    public static Answer toEntity() {
        return Answer.builder().
                answerId(answerId).
                content(content).
                date(date).
                question(question).
                admin(admin).build();
    }
}