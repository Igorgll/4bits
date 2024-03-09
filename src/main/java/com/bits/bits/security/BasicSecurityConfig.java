package com.bits.bits.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }
        
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .csrf(csrf -> csrf.disable())
                                .headers((headers) -> headers.disable())
                                .cors(withDefaults());

                http
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers("/api/v1/users/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                                .anyRequest().permitAll())
                                .httpBasic(withDefaults());

                return http.build();

        }

}