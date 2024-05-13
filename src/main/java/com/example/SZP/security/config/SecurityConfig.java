package com.example.SZP.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("ALL")
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .defaultSuccessUrl("/tutorials", true); // Przekierowanie na /tutorials

        return http.build();
    }
}
