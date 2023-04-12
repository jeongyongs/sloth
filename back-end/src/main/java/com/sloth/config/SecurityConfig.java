package com.sloth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("api/check/username", "api/login", "api/members", "api/members/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout.permitAll())
                .build();
    }
}
