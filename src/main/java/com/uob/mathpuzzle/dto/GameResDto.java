package com.uob.mathpuzzle.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameResDto {
    private Long score_id;
    private Boolean isTrue;
    private String question;
    private int solution;

    public GameResDto(String question, int solution) {
        this.question = question;
        this.solution = solution;
    }
}
