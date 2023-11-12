package com.uob.mathpuzzle.service;

import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.dto.ScoreDTO;
import com.uob.mathpuzzle.dto.ScoreLevelDTO;
import com.uob.mathpuzzle.entity.Score;

public interface GameService {

    GameResDto getNewQuestion();

    ScoreLevelDTO getScoreLevelInStart(String token);
    ScoreDTO saveScoreLevel(String token, ScoreDTO scoreDTO);
}