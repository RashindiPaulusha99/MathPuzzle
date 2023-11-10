package com.uob.mathpuzzle.service;

import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.dto.ScoreLevelDTO;

public interface GameService {
    GameResDto getNewQuestion();

    ScoreLevelDTO getScoreLevelInStart(String token);
}
