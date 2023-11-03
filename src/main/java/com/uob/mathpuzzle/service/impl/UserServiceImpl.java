package com.uob.mathpuzzle.service.impl;

import com.ceyentra.wowreferralguru.constant.OAuth2Constant;
import com.ceyentra.wowreferralguru.dto.AdminUserDTO;
import com.ceyentra.wowreferralguru.dto.UserDTO;
import com.ceyentra.wowreferralguru.dto.responseDTOs.AuthDetailsDTO;
import com.ceyentra.wowreferralguru.entity.*;
import com.ceyentra.wowreferralguru.enums.UserRole;
import com.ceyentra.wowreferralguru.enums.UserStatus;
import com.ceyentra.wowreferralguru.exception.WowReferralGuruServiceException;
import com.ceyentra.wowreferralguru.repository.ReferralUserRepository;
import com.ceyentra.wowreferralguru.repository.UserRepository;
import com.ceyentra.wowreferralguru.service.UserService;
import com.ceyentra.wowreferralguru.util.DecodeToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static com.ceyentra.wowreferralguru.constant.ApplicationConstant.PRECONDITION_FAIL;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    ReferralUserRepository referralUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DecodeToken decodeToken;

    @Value("${token.uri}")
    private String tokenUri;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, RestTemplate restTemplate, BCryptPasswordEncoder encoder) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.encoder = encoder;
    }

    @Override
    public AuthDetailsDTO saveNewUser(UserDTO userDTO) {
        log.info("Execute method saveNewUser :" + " user :" + userDTO);

        try {

            User userByContactNumber = referralUserRepository.findUserByContactNumber(userDTO.getContactNumber());

            if (userByContactNumber == null) {

                User user = new User();

                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setContactNumber(userDTO.getContactNumber());
                user.setIdentifier(userDTO.getIdentifier());
                user.setReferralCode(UUID.randomUUID().toString().concat(new StringBuilder(userDTO.getContactNumber()).reverse().toString()));
                user.setTotalReferrals(0);
                user.setEzEarnings(BigDecimal.valueOf(0.00));
                user.setStarPointsEarnings(0);
                user.setStatus(UserStatus.ACTIVE);
                user.setRewards(null);

                // save user
                User newUser = referralUserRepository.save(user);

                // get generated auth token for new user
                JsonObject jsonObject = generateToken(newUser.getId());

                AuthDetailsDTO authDetailsDTO = new AuthDetailsDTO();
                authDetailsDTO.setAccess_token(String.valueOf(jsonObject.get("access_token")).replaceAll("\"", ""));
                authDetailsDTO.setToken_type(String.valueOf(jsonObject.get("token_type")).replaceAll("\"", ""));
                authDetailsDTO.setScope(String.valueOf(jsonObject.get("scope")).replaceAll("\"", ""));
                authDetailsDTO.setExpires_in(Integer.valueOf(String.valueOf(jsonObject.get("expires_in")).replaceAll("\"", "")));
                authDetailsDTO.setNewUser(true);

                return authDetailsDTO;
            } else {
                // get generated auth token for existing user
                JsonObject jsonObject = generateToken(userByContactNumber.getId());

                AuthDetailsDTO authDetailsDTO = new AuthDetailsDTO();
                authDetailsDTO.setAccess_token(String.valueOf(jsonObject.get("access_token")).replaceAll("\"", ""));
                authDetailsDTO.setToken_type(String.valueOf(jsonObject.get("token_type")).replaceAll("\"", ""));
                authDetailsDTO.setScope(String.valueOf(jsonObject.get("scope")).replaceAll("\"", ""));
                authDetailsDTO.setExpires_in(Integer.valueOf(String.valueOf(jsonObject.get("expires_in")).replaceAll("\"", "")));
                authDetailsDTO.setNewUser(false);

                return authDetailsDTO;
            }

        } catch (Exception e) {
            log.error("Error at method saveNewUser: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer getUserCount(String token) {
        log.info("Execute method getUserCount ");

        try {

            // get admin from token
            AdminUserDTO user = decodeToken.checkAccessTokenAndGetAdminUser(token);

            // check user role is equal to ADMIN
            if (user.getUserRole() != UserRole.ADMIN) {
                throw new WowReferralGuruServiceException(PRECONDITION_FAIL, "Not allowed to get user count.");
            }else {

                return referralUserRepository.getUserCount();
            }

        } catch (HttpStatusCodeException e) {
            log.error("Error at method getUserCount : " + e.getMessage());
            throw e;
        }
    }

    public JsonObject generateToken(Long id) {
        log.info("Execute method generateToken ");

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", OAuth2Constant.BASIC_TOKEN);
            headers.set("user_id", String.valueOf(id));

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("grant_type", OAuth2Constant.GRANT_TYPE_CLIENT_CREDENTIALS);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(tokenUri, HttpMethod.POST, request, String.class);

            JsonObject json = new JsonParser().parse(response.getBody()).getAsJsonObject();

            json.addProperty("user_id",id);

            return json;

        } catch (HttpStatusCodeException e) {
            log.error("Error at method generateToken : " + e.getMessage());
            throw e;
        }

    }


}
