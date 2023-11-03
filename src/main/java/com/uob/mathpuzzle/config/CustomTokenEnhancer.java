package com.uob.mathpuzzle.config;

import com.ceyentra.wowreferralguru.constant.OAuth2Constant;
import com.ceyentra.wowreferralguru.dto.AdminUserDTO;
import com.ceyentra.wowreferralguru.exception.WowReferralGuruServiceException;
import com.ceyentra.wowreferralguru.repository.AdminRepository;
import com.ceyentra.wowreferralguru.service.AdminService;
import com.ceyentra.wowreferralguru.service.Oauth2UserService;
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
    private final AdminService adminService;

    @Autowired
    public CustomTokenEnhancer(Oauth2UserService oauth2UserService, HttpServletRequest request, BCryptPasswordEncoder encoder, AdminRepository adminRepository, AdminService adminService) {
        this.oauth2UserService = oauth2UserService;
        this.request = request;
        this.encoder = encoder;
        this.adminService = adminService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>();

        String grantType = oAuth2Authentication.getOAuth2Request().getGrantType();

        if (grantType.equals(OAuth2Constant.GRANT_TYPE)){

            User user = (User) oAuth2Authentication.getPrincipal();

            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            User account = (User) authentication.getPrincipal();

            if (account.getUsername().equals(OAuth2Constant.CLIENT_ID)) {

                AdminUserDTO adminDetails = adminService.getAdminDetails(user.getUsername());
                additionalInfo.put("user",adminDetails);

            } else {

            }

            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

            return super.enhance(oAuth2AccessToken, oAuth2Authentication);

        }else if (grantType.equals(OAuth2Constant.GRANT_TYPE_CLIENT_CREDENTIALS)){

            String userId = null;
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);

                if (headerName.equals("user_id")){
                    userId = headerValue;
                }
            }

            String username = (String) oAuth2Authentication.getPrincipal();

            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            User account = (User) authentication.getPrincipal();

            if (userId == null){

            }else {
                if (account.getUsername().equals(OAuth2Constant.CLIENT_ID)) {

                    additionalInfo.put("user_id",userId);
                    additionalInfo.put("authorities", new String[]{"ROLE_USER"});

                } else {

                }
            }

            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

            return super.enhance(oAuth2AccessToken, oAuth2Authentication);
        }else {
            throw new WowReferralGuruServiceException(400,"Invalid grant type.");
        }

    }
}
