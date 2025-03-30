package com.timesheet.TSbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final VerifyToken firebaseTokenFilter;

    // Constructor injection: Spring will automatically provide the VerifyToken bean here
    @Autowired
    public SecurityConfig(VerifyToken firebaseTokenFilter) {
        this.firebaseTokenFilter = firebaseTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1) CSRF
        http.csrf(csrf -> csrf.disable());

        // 2) Session Management
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 3) Authorization Rules
        http.authorizeHttpRequests(
                auth ->auth.anyRequest().permitAll()
        );

        // 4) Custom Filter
        http.addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 5) Build the SecurityFilterChain
        return http.build();
    }
}
