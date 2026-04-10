package com.homeLoan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/account/create/**").hasRole("USER")
                .antMatchers("/account/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/loan/apply").hasRole("USER")
                .antMatchers("/loan/pay-emi/**").hasRole("USER")
                .antMatchers("/loan/document").hasAnyRole("ADMIN", "USER")
                .antMatchers("/loan/prepay").hasRole("USER")
                .antMatchers("/loan/foreclose/**").hasRole("USER")
                .antMatchers("/loan/approve/**").hasRole("ADMIN")
                .antMatchers("/loan/cancel/**").hasRole("ADMIN")
                .antMatchers("/loan/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}