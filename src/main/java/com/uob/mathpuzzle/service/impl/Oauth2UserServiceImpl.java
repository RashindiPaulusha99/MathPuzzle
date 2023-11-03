package com.uob.mathpuzzle.service.impl;

import com.uob.mathpuzzle.exception.CustomOauthException;
import com.uob.mathpuzzle.repository.MathRepository;
import com.uob.mathpuzzle.service.Oauth2UserService;
import com.uob.mathpuzzle.util.EmailValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.uob.mathpuzzle.constant.OAuth2Constant.CLIENT_ID;

/**
 * Handle user details service for user token generating process
 */
@Log4j2
@Service(value = "userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class Oauth2UserServiceImpl implements Oauth2UserService, UserDetailsService {

    private final MathRepository mathRepository;
    private final EmailValidator emailValidator;

    @Autowired
    public Oauth2UserServiceImpl(MathRepository mathRepository, EmailValidator emailValidator) {
        this.mathRepository = mathRepository;
        this.emailValidator = emailValidator;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Execute method loadUserByUsername :"+s);

        try {

            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            User user = (User) authentication.getPrincipal();
            String clientId = user.getUsername();

            // check email is valid
            if(!emailValidator.isValidEmail(s)) throw new CustomOauthException("Invalid Credential Format");

            // check client credentials
            if(clientId.equals(CLIENT_ID)) {
                //if admin
                Optional<com.uob.mathpuzzle.entity.User> byEmail = mathRepository.findByEmail(s);

                if(!byEmail.isPresent()) throw new CustomOauthException("Invalid Credentials");

                // get user role
                return new User(byEmail.get().getEmail(), byEmail.get().getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

            }else {
                return null;
            }

        } catch (Exception e) {
            log.error("Error at method loadUserByUsername: " + e.getMessage());
            throw e;
        }

    }
}
