package com.uob.mathpuzzle.service.impl;

import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.dto.PlayerDTO;
import com.uob.mathpuzzle.dto.ScoreDTO;
import com.uob.mathpuzzle.dto.ScoreLevelDTO;
import com.uob.mathpuzzle.entity.Player;
import com.uob.mathpuzzle.entity.Score;
import com.uob.mathpuzzle.repository.GameRepository;
import com.uob.mathpuzzle.service.GameService;
import com.uob.mathpuzzle.util.DecodeToken;
import com.uob.mathpuzzle.util.NowDate;
import com.uob.mathpuzzle.util.TomatoAPI;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GameServiceImpl implements GameService{

    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder encoder;
    private final TomatoAPI tomatoAPI;
    private final GameRepository gameRepository;
    private final DecodeToken decodeToken;
    private final NowDate nowDate;

    @Autowired
    public GameServiceImpl(ModelMapper modelMapper, RestTemplate restTemplate, BCryptPasswordEncoder encoder, TomatoAPI tomatoAPI, GameRepository gameRepository, DecodeToken decodeToken, NowDate nowDate) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.encoder = encoder;
        this.tomatoAPI = tomatoAPI;
        this.gameRepository = gameRepository;
        this.decodeToken = decodeToken;
        this.nowDate = nowDate;
    }

    @Override
    public GameResDto getNewQuestion() {
        log.info("Execute method getNewQuestion :");

        try {
            return tomatoAPI.getNewQuestion();
        } catch (Exception e) {
            log.error("Error at method getNewQuestion: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public ScoreLevelDTO getScoreLevelInStart(String token) {

        log.info("Execute method getScoreLevelInStart :");

        try {
            // get player from token
            PlayerDTO playerDTO = decodeToken.checkAccessTokenAndGetPlayer(token);

            ScoreLevelDTO scoreLevelDTO = new ScoreLevelDTO();
            scoreLevelDTO.setLevel(gameRepository.findByLevel(playerDTO.getId()));
            scoreLevelDTO.setScore(gameRepository.findByScores(playerDTO.getId()) == null ? 0 : gameRepository.findByScores(playerDTO.getId()));

            return scoreLevelDTO;
        } catch (Exception e) {
            log.error("Error at method getScoreLevelInStart: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public ScoreDTO saveScoreLevel(String token, ScoreDTO scoreDTO) {
        log.info("Execute method saveScoreLevel :"+ "ScoreDTO : "+scoreDTO);

        try {
            // get player from token
            PlayerDTO playerDTO = decodeToken.checkAccessTokenAndGetPlayer(token);

            if (scoreDTO.getId() == null){
                Score score = new Score();
                score.setPlayer(modelMapper.map(playerDTO, Player.class));
                score.setQuestion_link(scoreDTO.getQuestion_link());
                score.setAnswer(scoreDTO.getAnswer());
                score.setIs_correct(scoreDTO.getIs_correct());
                score.setLevel(scoreDTO.getLevel());
                score.setScore(scoreDTO.getScore());
                score.setUpdatedTimestamp(new Date());

                return modelMapper.map(gameRepository.save(score),ScoreDTO.class);
            }else {
                Optional<Score> byId = gameRepository.findById(scoreDTO.getId());

                byId.get().setIs_correct(scoreDTO.getIs_correct());
                byId.get().setScore(scoreDTO.getScore());
                byId.get().setUpdatedTimestamp(new Date());

                return modelMapper.map(gameRepository.save(byId.get()),ScoreDTO.class);
            }

        } catch (Exception e) {
            log.error("Error at method ScoreLevelDTO: " + e.getMessage());
            throw e;
        }
    }
}