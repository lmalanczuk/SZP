package com.example.SZP.security.config;

import com.example.SZP.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("ALL")
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig implements WebSecurityCustomizer {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers("/api/v1/registration/**");
        web.ignoring().requestMatchers("/api/v1/tutorials");
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
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
                    .requestMatchers( "/api/v1/tutorials").permitAll() // Pozwól na dostęp do POST /api/v1/tutorials bez uwierzytelniania
                    .anyRequest().authenticated();

    }


}