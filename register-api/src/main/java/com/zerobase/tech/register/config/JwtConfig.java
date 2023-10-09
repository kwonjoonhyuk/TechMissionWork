package com.zerobase.tech.register.config;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// JWT Bean 등록
@Configuration
public class JwtConfig {

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider (){
        return new JwtAuthenticationProvider();
    }
}
