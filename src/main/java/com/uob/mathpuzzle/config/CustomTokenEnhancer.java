package com.uob.mathpuzzle.config;

import com.uob.mathpuzzle.constant.OAuth2Constant;
import com.uob.mathpuzzle.dto.UserDTO;
import com.uob.mathpuzzle.exception.MathException;
import com.uob.mathpuzzle.repository.UserRepository;
import com.uob.mathpuzzle.service.Oauth2UserService;
import com.uob.mathpuzzle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    private final Oauth2UserService oauth2UserService;
    private final HttpServletRequest request;
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public CustomTokenEnhancer(Oauth2UserService oauth2UserService, HttpServletRequest request, BCryptPasswordEncoder encoder, UserRepository userRepository, UserService userService) {
        this.oauth2UserService = oauth2UserService;
        this.request = request;
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>();

        User user = (User) oAuth2Authentication.getPrincipal();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User account = (User) authentication.getPrincipal();

        if (account.getUsername().equals(OAuth2Constant.CLIENT_ID)) {

            UserDTO adminDetails = userService.getAdminDetails(user.getUsername());
            additionalInfo.put("user",adminDetails);

        } else {

        }

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

        return super.enhance(oAuth2AccessToken, oAuth2Authentication);
    }
}
