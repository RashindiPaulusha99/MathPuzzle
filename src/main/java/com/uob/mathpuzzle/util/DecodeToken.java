package com.uob.mathpuzzle.util;

import com.uob.mathpuzzle.config.CustomUserAuthenticator;
import com.uob.mathpuzzle.dto.UserDTO;
import com.uob.mathpuzzle.entity.User;
import com.uob.mathpuzzle.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DecodeToken {

    private final ModelMapper modelMapper;

    @Autowired
    UserRepository adminRepo;

    @Autowired
    ReferralUserRepository referralUserRepository;

    public DecodeToken(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // get admin from token
    public UserDTO checkAccessTokenAndGetAdminUser(String token) {
        log.info("Execute method checkAccessTokenAndGetAdminUser ");

        try {

            // get user object from token
            JSONObject userFromToken = CustomUserAuthenticator.getUserFromToken(token);

            //get admin by email
            return findAdminUserByEmail(userFromToken.getString("user_name"));

        } catch (Exception e) {
            log.error("Error at method checkAccessTokenAndGetAdminUser: " + e.getMessage());
            throw e;
        }

    }

    // get user from token
    public UserDTO checkAccessTokenAndGetUser(String token) {
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
    private UserDTO findAdminUserByEmail(String email){
        log.info("Execute method findAdminUserByEmail :"+email);

        try {

            // get admin by email
            User adminUsersByEmail = adminRepo.findByEmail(email);
            return modelMapper.map(adminUsersByEmail,UserDTO.class);

        } catch (Exception e) {
            log.error("Error at method findAdminUserByEmail: " + e.getMessage());
            throw e;
        }
    }

    // find user by user id
    private UserDTO findUserById(Long id){
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