package com.uob.mathpuzzle.service;

import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.dto.LeaderboardDTO;
import com.uob.mathpuzzle.dto.ScoreDTO;
import com.uob.mathpuzzle.dto.ScoreLevelDTO;

import java.util.List;

public interface GameService {

    GameResDto getNewQuestion();

    ScoreLevelDTO getScoreLevelInStart(String token);
    List<LeaderboardDTO> getLeaderboard(String token);
    ScoreDTO saveScoreLevel(String token, ScoreDTO scoreDTO);
}