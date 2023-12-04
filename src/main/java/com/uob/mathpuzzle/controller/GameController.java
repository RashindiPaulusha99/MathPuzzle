package com.uob.mathpuzzle.controller;

import com.uob.mathpuzzle.dto.*;
import com.uob.mathpuzzle.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.uob.mathpuzzle.constant.OAuth2Constant.HEADER_AUTH;

@RestController
@RequestMapping("/v1/game")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin
public class GameController {

    @Autowired
    private GameService gameService;

    // start game
    @GetMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponseDTO playGame() {
        try {
            log.info("REST request to get new game ");
            return new CommonResponseDTO<GameResDto>(true, "Success", gameService.getNewQuestion());
        } catch (Exception e) {
            log.error("Error getting game: " + e.getMessage());
            // Return an error response or handle the exception as necessary
            return new CommonResponseDTO<GameResDto>(false, "Error: " + e.getMessage(), null);
        }
    }

    // get score and level
    @GetMapping(path = "/get/start/scoreLevel", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponseDTO getScoreAndLevel(
            @RequestHeader(value = HEADER_AUTH, required = true) String token
    ) {
        try {
            log.info("REST request to get score and level in start ");

            if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
                return new CommonResponseDTO<>(false, "Bearer token is required", null);
            }

            return new CommonResponseDTO<ScoreLevelDTO>(true, "Success", gameService.getScoreLevelInStart(token));
        } catch (Exception e) {
            log.error("Error getting score and level: " + e.getMessage());
            // Return an error response or handle the exception as necessary
            return new CommonResponseDTO<ScoreLevelDTO>(false, "Error: " + e.getMessage(), null);
        }
    }

    // save score and level
    @PostMapping(path = "/save/scoreLevel", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponseDTO saveScoreAndLevel(
            @RequestHeader(value = HEADER_AUTH, required = true) String token,
            @RequestBody ScoreDTO scoreDTO
    ) {
        try {
            log.info("REST request to save score and level "+ "score :"+scoreDTO);

            if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
                return new CommonResponseDTO<>(false, "Bearer token is required", null);
            }
            return new CommonResponseDTO<ScoreDTO>(true, "Success",gameService.saveScoreLevel(token,scoreDTO));
        } catch (Exception e) {
            log.error("Error saving score and level: " + e.getMessage());
            // Return an error response or handle the exception as necessary
            return new CommonResponseDTO<ScoreDTO>(false, "Error: " + e.getMessage(), null);
        }
    }

    // get all score , users, rewards to display in leaderboard
    @GetMapping(path = "/get/leaderboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponseDTO getLeaderboard(
            @RequestHeader(value = HEADER_AUTH, required = true) String token
    ) {
        try {
            log.info("REST request to get score, users, reward to display in leaderboard");

            if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
                return new CommonResponseDTO<>(false, "Bearer token is required", null);
            }

            return new CommonResponseDTO<LeaderboardDTO>(true, "Success", gameService.getLeaderboard(token));
        } catch (Exception e) {
            log.error("Error getting score, users, reward: " + e.getMessage());
            // Return an error response or handle the exception as necessary
            return new CommonResponseDTO<LeaderboardDTO>(false, "Error: " + e.getMessage(), null);
        }
    }

}
