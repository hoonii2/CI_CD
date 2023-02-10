package com.example.ci_cd.security.configuration;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService; // UserDetailsService 를 구현하는 구현체 연결을 위함

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/member/new").permitAll() // 사용자 추가를 위한 URL 은 허용
                    .antMatchers("/admin").hasRole("ADMIN") // ADMIN 권한 사용자만 접근 가능
                    .anyRequest().authenticated() // 위 나머지 경로는 인증 수행
                    .and()
                .formLogin() // 기본 Form 으로 로그인하도록 수행 ( /login )
                    .defaultSuccessUrl("/main").permitAll() // 로그인 성공 시 /main 으로 접근 수행
                    .and()
                .logout(); // 로그아웃 기능 구현

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
