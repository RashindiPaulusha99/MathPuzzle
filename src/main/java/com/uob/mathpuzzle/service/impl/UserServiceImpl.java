package com.uob.mathpuzzle.service.impl;

import com.uob.mathpuzzle.dto.UserDTO;
import com.uob.mathpuzzle.entity.User;
import com.uob.mathpuzzle.exception.MathException;
import com.uob.mathpuzzle.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getAdminDetails(String email) {
        log.info("Execute method getAdminDetails :"+email);

        try {

            // check admin
            Optional<User> byEmail = userRepository.findByEmail(email);
            if(!byEmail.isPresent()) throw new MathException("Admin user not found");

            // get admin
            return modelMapper.map(byEmail.get(), UserDTO.class);

        } catch (Exception e) {
            log.error("Error at method getAdminDetails: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }
}
