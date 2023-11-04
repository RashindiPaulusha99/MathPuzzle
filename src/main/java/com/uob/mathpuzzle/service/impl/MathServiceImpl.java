package com.uob.mathpuzzle.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uob.mathpuzzle.constant.OAuth2Constant;
import com.uob.mathpuzzle.util.DecodeToken;
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


@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MathServiceImpl{

    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder encoder;


    @Autowired
    DecodeToken decodeToken;

    @Value("${token.uri}")
    private String tokenUri;

    @Autowired
    public MathServiceImpl(ModelMapper modelMapper, RestTemplate restTemplate, BCryptPasswordEncoder encoder) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.encoder = encoder;
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
