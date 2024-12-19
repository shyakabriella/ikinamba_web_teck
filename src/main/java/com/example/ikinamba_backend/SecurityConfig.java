package com.example.ikinamba_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity in development
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No session will be created or used by spring security
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll() // Allow public access to login and register endpoints
                        .requestMatchers("/api/carwash/services", "/api/bookings/**").permitAll() // Allow public access to services and all booking related endpoints
                        .requestMatchers("/api/price_settings/**").permitAll() // Allow public access to price settings
                        .requestMatchers("/api/requests/**").permitAll() // Only allow clients and admins to manage requests
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .httpBasic(); // Use basic authentication for simplicity

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
