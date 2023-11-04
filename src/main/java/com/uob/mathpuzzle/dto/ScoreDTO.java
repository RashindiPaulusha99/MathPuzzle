package com.uob.mathpuzzle.dto;

import com.uob.mathpuzzle.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ScoreDTO {

    private Long id;
    private Player player;
    private String question_link;
    private double answer;
    private boolean is_correct;
    private Date updatedTimestamp;
}
