package com.uob.mathpuzzle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Player player;
    @Lob
    private String question_link;
    private int answer;
    private int level;
    private Boolean is_correct;
    private int score;
    private double timer;
    private int reward;
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTimestamp;

}