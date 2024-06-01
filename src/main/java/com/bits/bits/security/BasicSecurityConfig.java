package com.bits.bits.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.bits.bits.service.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                        .requestMatchers(
                                "/api/v1/admins/login",
                                "/api/v1/orders/all",
                                "/api/v1/orders/updateStatus/{orderId}",
                                "/api/v1/products/createProduct",
                                "/api/v1/products/isProductActive/status",
                                "/api/v1/products/updateProduct/{productId}")
                        .hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(
                                "/h2-console/**",
                                "/api/v1/orders/{orderId}",
                                "/api/v1/orders/create",
                                "/api/v1/users/**",
                                "/api/v1/admins/signup",
                                "/api/v1/products/all",
                                "/api/v1/products/productId/{productId}",
                                "/api/v1/products/productName/{productName}",
                                "/api/v1/products/productImage/{productId}",
                                "/api/v1/cart/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

        return http.build();
    }
}