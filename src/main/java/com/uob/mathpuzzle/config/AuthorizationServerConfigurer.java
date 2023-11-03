package com.uob.mathpuzzle.config;

import com.uob.mathpuzzle.exception.CustomOauthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

import static com.uob.mathpuzzle.constant.OAuth2Constant.*;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encoder;
    private  CustomTokenEnhancer customTokenEnhancer;

    @Autowired
    public void AuthorizationServerConfig(AuthenticationManager authenticationManager, BCryptPasswordEncoder encoder, CustomTokenEnhancer customTokenEnhancer) {
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.customTokenEnhancer = customTokenEnhancer;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()

                .withClient(CLIENT_ID)
                .secret(encoder.encode(CLIENT_SECRET))
                .authorizedGrantTypes(GRANT_TYPE, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT,GRANT_TYPE_CLIENT_CREDENTIALS)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
               //.pathMapping("/oauth/token", "/v1/authorize")
                .exceptionTranslator(exception -> {
                    if (exception instanceof InvalidGrantException)
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomOauthException("invalid credentials."));
                    else
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomOauthException(
                                (exception.getMessage()) != null ? exception.getMessage() : "Sorry, something went wrong."));
                });
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(TOKEN_SIGN_IN_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return customTokenEnhancer;
    }

}
