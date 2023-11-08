package com.uob.mathpuzzle.service.impl;

import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.service.GameService;
import com.uob.mathpuzzle.util.DecodeToken;
import com.uob.mathpuzzle.util.TomatoAPI;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GameServiceImpl implements GameService{

    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder encoder;
    private final TomatoAPI tomatoAPI;

    @Autowired
    public GameServiceImpl(ModelMapper modelMapper, RestTemplate restTemplate, BCryptPasswordEncoder encoder,TomatoAPI tomatoAPI) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.encoder = encoder;
        this.tomatoAPI = tomatoAPI;
    }


    @Override
    public GameResDto getNewQuestion() {
        return tomatoAPI.getNewQuestion();
    }
}
