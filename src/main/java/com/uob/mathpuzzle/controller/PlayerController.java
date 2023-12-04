package com.uob.mathpuzzle.controller;

import com.uob.mathpuzzle.dto.CommonResponseDTO;
import com.uob.mathpuzzle.dto.PlayerDTO;
import com.uob.mathpuzzle.dto.ScoreLevelDTO;
import com.uob.mathpuzzle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.uob.mathpuzzle.constant.OAuth2Constant.HEADER_AUTH;

@RestController
@RequestMapping("/v1/player")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin
public class PlayerController {

    @Autowired
    private UserService playerService;

    // register player
    @PostMapping(path = "/register/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponseDTO savePlayer(@RequestBody PlayerDTO playerDTO) {
        try {
            log.info("REST request to register new player " + "player :" + playerDTO);
            return new CommonResponseDTO<PlayerDTO>(true, "Success", playerService.savePlayer(playerDTO));
        } catch (Exception e) {
            log.error("Error saving player: " + e.getMessage());
            // Return an error response or handle the exception as necessary
            return new CommonResponseDTO<PlayerDTO>(false, "Error: " + e.getMessage(), null);
        }
    }

    // get user details
    @GetMapping(path = "/get/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponseDTO getPlayerDetails(
            @RequestHeader(value = HEADER_AUTH, required = true) String token
    ) {
        try {
            log.info("REST request to get player details ");

            if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
                return new CommonResponseDTO<>(false, "Bearer token is required", null);
            }

            return new CommonResponseDTO<PlayerDTO>(true, "Success", playerService.getPlayer(token));
        } catch (Exception e) {
            log.error("Error getting player details: " + e.getMessage());
            // Return an error response or handle the exception as necessary
            return new CommonResponseDTO<PlayerDTO>(false, "Error: " + e.getMessage(), null);
        }
    }
}

