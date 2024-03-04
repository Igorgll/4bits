package com.bits.bits.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@Configuration
@EnableWebSecurity

public class BasicSecurityConfig extends WebSecurityConfiguration {

//    @Bean
//    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
//        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults());
//
//        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll().authenticationProvider(authenticationProvider())
//                .httpBasic(withDefaults()));
//
//
//
//        return http.build();
// }

    @Override
    protected void configure (HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
    }

}
