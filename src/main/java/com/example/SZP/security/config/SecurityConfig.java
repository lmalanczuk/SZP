package com.example.SZP.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("ALL")
@Configuration
public class SecurityConfig implements WebSecurityCustomizer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .defaultSuccessUrl("/api/v1", true).permitAll(); // Przekierowanie na /api/v1 (ten sam adres podany jest w TutorialController na początku klasy)

        return http.build();
    }

    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .requestMatchers(HttpMethod.POST, "/api/v1/tutorials").permitAll() // Pozwól na dostęp do POST /api/v1/tutorials bez uwierzytelniania
                    .anyRequest().authenticated()
                .and()
                .formLogin().disable(); // Wyłącz formularz logowania, jeśli nie jest potrzebny
    }

    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers("/api/v1/tutorials");
    }
}