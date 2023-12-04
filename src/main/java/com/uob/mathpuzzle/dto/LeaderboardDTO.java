package com.uob.mathpuzzle.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LeaderboardDTO {

    private int rank;
    private String name;
    private int score;
    private int reward;

}
