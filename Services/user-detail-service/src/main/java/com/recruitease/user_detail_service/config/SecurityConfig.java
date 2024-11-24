package com.recruitease.user_detail_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // for pre post authorization for roles
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(userContextFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/candidate/**",
                                "/user/admin/**",
                                "/user/recruiter/**",
                                "/user/moderator/**",
                                "/user/admin-list",
                                "/user/candidate-list",
                                "/user/moderator-list",
                                "/user/recruiter-list",
                                "/user/detail-list")
                        .permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter(new ModelMapper());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/user/detail-list");
    }
}
