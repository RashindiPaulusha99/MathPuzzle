package com.uob.mathpuzzle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .httpStrictTransportSecurity()
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000)
                        .preload(false)
                    .and()
                    .frameOptions()
                        .sameOrigin()
                    .xssProtection()
                    .and()
                    .contentSecurityPolicy("default-src 'self'; " +
                            "script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +
                            "style-src 'self' 'unsafe-inline'; " +
                            "img-src 'self' data:; " +
                            "font-src 'self'; " +
                            "frame-src 'self'; " +
                            "frame-ancestors 'self';")
                    .and()
                    //.addHeaderWriter(rateLimitHeaderWriter())
                    .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "DENY"))
                    .addHeaderWriter(new StaticHeadersWriter("X-Rate-Limit-Limit", "10"))
                    .addHeaderWriter(new StaticHeadersWriter("X-Rate-Limit-Remaining", "60"))
                    .addHeaderWriter(new StaticHeadersWriter("X-Rate-Limit-Reset", "60"))
                    .and()
                    .csrf().disable()
                    .anonymous().disable()
                    .authorizeRequests()
                    .antMatchers("/api-docs/**").permitAll();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:3001");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        /*firewall.setStrict(true);*/
        List<String> allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE");
        firewall.setAllowedHttpMethods(allowedMethods);
        return firewall;
    }

    private HeaderWriter rateLimitHeaderWriter() {
        return (request, response) -> {
            response.setHeader("X-RateLimit-Limit", "10"); // Set the rate limit value
            response.setHeader("X-RateLimit-Remaining", "60"); // Set the remaining requests value
            response.setHeader("X-RateLimit-Reset", "1628592000"); // Set the reset timestamp
        };
    }


}
