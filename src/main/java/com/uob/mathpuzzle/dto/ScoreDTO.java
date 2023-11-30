package com.uob.mathpuzzle.dto;

import com.uob.mathpuzzle.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ScoreDTO {

    private Long id;
    private String question_link;
    private int answer;
    private int level;
    private Boolean is_correct;
    private int score;
    private double timer;
    private int reward;

}