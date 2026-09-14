package com.tarek.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder getpasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    )
    {
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            DaoAuthenticationProvider provider
    )
    {
        httpSecurity.csrf(c->c.disable())
                .authenticationProvider(provider)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/register","/api/auth/register").permitAll()
                                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")

                                .anyRequest().authenticated()
                        );


       return httpSecurity.build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
    {
        return authConfig.getAuthenticationManager();
    }

}
