package com.uob.mathpuzzle.controller;

import com.uob.mathpuzzle.dto.CommonResponseDTO;
import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.dto.PlayerDTO;
import com.uob.mathpuzzle.service.GameService;
import com.uob.mathpuzzle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
}
