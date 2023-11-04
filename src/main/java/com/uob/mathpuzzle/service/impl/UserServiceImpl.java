package com.uob.mathpuzzle.service.impl;

import com.uob.mathpuzzle.dto.PlayerDTO;
import com.uob.mathpuzzle.entity.Player;
import com.uob.mathpuzzle.exception.GameException;
import com.uob.mathpuzzle.repository.PlayerRepository;
import com.uob.mathpuzzle.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Log4j2
@Service
@CrossOrigin
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
        Player player = new Player();
        player.setEmail(playerDTO.getEmail());
        player.setUsername(playerDTO.getUsername());
        player.setPassword(passwordEncoder.encode(playerDTO.getPassword()));
        return modelMapper.map(playerRepository.save(player), PlayerDTO.class);
    }
}
