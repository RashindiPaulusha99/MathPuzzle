package com.uob.mathpuzzle.util;

import com.uob.mathpuzzle.config.CustomUserAuthenticator;
import com.uob.mathpuzzle.dto.PlayerDTO;
import com.uob.mathpuzzle.entity.Player;
import com.uob.mathpuzzle.repository.PlayerRepository;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log4j2
public class DecodeToken {

    private final ModelMapper modelMapper;

    @Autowired
    PlayerRepository playerRepository;

    public DecodeToken(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // get admin from token
    public PlayerDTO checkAccessTokenAndGetPlayer(String token) {
        log.info("Execute method checkAccessTokenAndGetAdminUser ");

        try {

            // get user object from token
            JSONObject userFromToken = CustomUserAuthenticator.getUserFromToken(token);
            System.out.println(userFromToken);

            //get player by email
            return findPlayerByEmail(userFromToken.getString("user_name"));

        } catch (Exception e) {
            log.error("Error at method checkAccessTokenAndGetAdminUser: " + e.getMessage());
            throw e;
        }

    }

    // get user from token
    public PlayerDTO checkAccessTokenAndGetUser(String token) {
        log.info("Execute method checkAccessTokenAndGetUser ");

        try {

            // get user object from token
            JSONObject userFromToken = CustomUserAuthenticator.getUserFromToken(token);

            //get user by user id
            return findUserById(Long.valueOf(userFromToken.getString("user_id")));

        } catch (Exception e) {
            log.error("Error at method checkAccessTokenAndGetUser: " + e.getMessage());
            throw e;
        }

    }

    // find admin by email
    private PlayerDTO findPlayerByEmail(String email){
        log.info("Execute method findPlayerByEmail :"+email);

        try {

            // get admin by email
            Optional<Player> adminUsersByEmail = playerRepository.findByEmail(email);
            return modelMapper.map(adminUsersByEmail.get(), PlayerDTO.class);

        } catch (Exception e) {
            log.error("Error at method findPlayerByEmail: " + e.getMessage());
            throw e;
        }
    }

    // find user by user id
    private PlayerDTO findUserById(Long id){
        log.info("Execute method findUserById :"+id);
        return null;

        /*try {

            // get user by id
            User userById = referralUserRepository.findUserById(id);
            return modelMapper.map(userById,UserDTO.class);

        } catch (Exception e) {
            log.error("Error at method findUserById: " + e.getMessage());
            throw e;
        }*/
    }
}
