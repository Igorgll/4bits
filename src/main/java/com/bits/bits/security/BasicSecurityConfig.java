package com.bits.bits.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.bits.bits.service.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsServiceImpl customUserDetailsService, PasswordEncoder encoder) {
        DaoAuthenticationProvider customProvider = new DaoAuthenticationProvider();
        customProvider.setUserDetailsService(customUserDetailsService);
        customProvider.setPasswordEncoder(encoder);

        return new ProviderManager(customProvider);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/admins/login")
                        .hasAnyAuthority("ROLE_ADMIN")

                        .requestMatchers("api/v1/estoquistas/login")
                        .hasAnyAuthority("ROLE_ESTOQUISTA")
                        .requestMatchers(
                                "/h2-console/**",
                                "/api/v1/orders/**",
                                "/api/v1/users/**",
                                "/api/v1/admins/signup",
                                "/api/v1/estoquistas/signup",
                                "/api/v1/products/**",
                                "/api/v1/cart/**",
                                "/api/v1/estoquistas/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

        return http.build();
    }
}