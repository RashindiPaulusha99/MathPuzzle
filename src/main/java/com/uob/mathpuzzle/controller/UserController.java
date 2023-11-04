package com.uob.mathpuzzle.controller;

import com.uob.mathpuzzle.dto.CommonResponseDTO;
import com.uob.mathpuzzle.dto.UserDTO;
import com.uob.mathpuzzle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.uob.mathpuzzle.constant.OAuth2Constant.HEADER_AUTH;

@RestController
@RequestMapping("/v1/user")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // register user
    @PostMapping(path = "/register/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponseDTO saveUser(@RequestBody UserDTO userDTO) {
        try {
            log.info("REST request to register new user " + "user :" + userDTO);
            return new CommonResponseDTO<UserDTO>(true, "Success", userService.saveUser(userDTO));
        } catch (Exception e) {
            log.error("Error saving user: " + e.getMessage());
            // Return an error response or handle the exception as necessary
            return new CommonResponseDTO<UserDTO>(false, "Error: " + e.getMessage(), null);
        }
    }
}

