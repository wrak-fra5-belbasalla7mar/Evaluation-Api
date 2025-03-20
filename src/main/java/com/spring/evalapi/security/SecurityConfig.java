package com.spring.evalapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ratings/**").hasAnyRole("EMPLOYEE", "TEAM_MANAGER", "COMPANY_MANAGER")
                        .requestMatchers("/kpis/**").hasAnyRole("TEAM_MANAGER", "COMPANY_MANAGER")
//                        .requestMatchers("/cycles/**").hasAnyRole("EMPLOYEE","TEAM_MANAGER", "COMPANY_MANAGER")
                        .requestMatchers("/profiles/**").hasRole("COMPANY_MANAGER")
                        .requestMatchers("/objectives/**").hasRole("TEAM_MANAGER")
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}



