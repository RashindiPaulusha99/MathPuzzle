package com.uob.mathpuzzle.service;

import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.dto.LeaderboardDTO;
import com.uob.mathpuzzle.dto.ScoreDTO;
import com.uob.mathpuzzle.dto.ScoreLevelDTO;

public interface GameService {

    GameResDto getNewQuestion();

    ScoreLevelDTO getScoreLevelInStart(String token);
    LeaderboardDTO getLeaderboard(String token);
    ScoreDTO saveScoreLevel(String token, ScoreDTO scoreDTO);
}