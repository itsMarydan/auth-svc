package com.blueint.authsvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http.csrf().disable();
        http.authorizeHttpRequests().requestMatchers("/api/v1/login/**", "/api/v1/token/refresh/**", "/api/v1/.well-known/jwks.json", "/api/v1/users", "/oauth/authorize**", "/login", "/oauth/token").permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeHttpRequests().requestMatchers("/api/v1/users/**").hasAnyAuthority("*");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.apply(new CustomDsl());
        return http.build();
    }

}
