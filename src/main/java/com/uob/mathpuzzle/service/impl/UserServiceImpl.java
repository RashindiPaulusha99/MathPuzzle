package com.uob.mathpuzzle.service.impl;

import com.uob.mathpuzzle.dto.PlayerDTO;
import com.uob.mathpuzzle.entity.Player;
import com.uob.mathpuzzle.exception.GameException;
import com.uob.mathpuzzle.repository.PlayerRepository;
import com.uob.mathpuzzle.service.UserService;
import com.uob.mathpuzzle.util.DecodeToken;
import com.uob.mathpuzzle.util.FileWriter;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Log4j2
@Service
@CrossOrigin
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DecodeToken decodeToken;
    private final FileWriter fileWriter;

    @Autowired
    public UserServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, DecodeToken decodeToken, FileWriter fileWriter) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.decodeToken = decodeToken;
        this.fileWriter = fileWriter;
    }

    @Override
    public PlayerDTO getAdminDetails(String email) {
        log.info("Execute method getAdminDetails :"+email);

        try {

            // check player
            Optional<Player> byEmail = playerRepository.findByEmail(email);
            if(!byEmail.isPresent()) throw new GameException("Admin user not found");

            // get player
            return modelMapper.map(byEmail.get(), PlayerDTO.class);

        } catch (Exception e) {
            log.error("Error at method getAdminDetails: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public PlayerDTO savePlayer(PlayerDTO playerDTO) {
        log.info("Execute method savePlayer :"+playerDTO);

        try {

            Player player = new Player();
            player.setEmail(playerDTO.getEmail());
            player.setUsername(playerDTO.getUsername());
            player.setPassword(passwordEncoder.encode(playerDTO.getPassword()));
            return modelMapper.map(playerRepository.save(player), PlayerDTO.class);

        } catch (Exception e) {
            log.error("Error at method savePlayer: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public PlayerDTO getPlayer(String token) {
        log.info("Execute method getPlayer :");

        try {

            // get player from token
            return decodeToken.checkAccessTokenAndGetPlayer(token);

        } catch (Exception e) {
            log.error("Error at method getPlayer: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public PlayerDTO saveProfileImage(String token, MultipartFile image) {
        log.info("Execute method saveProfileImage :"+"image : "+image);

        try {

            PlayerDTO playerDTO = decodeToken.checkAccessTokenAndGetPlayer(token);

            String filePath = null;
            // save image to htdocs and get its path url
            filePath = fileWriter.saveMultipartFile(image, "image");

            playerDTO.setImage(filePath);

            return modelMapper.map(playerRepository.save(modelMapper.map(playerDTO, Player.class)),PlayerDTO.class);

        } catch (Exception e) {
            log.error("Error at method saveProfileImage: " + e.getMessage());
            throw e;
        }
    }
}
