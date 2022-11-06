package com.example.habitstracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class WebSecurityCustomizerConfig {
    @Bean
    public WebSecurityCustomizer webCustomizer()
    {
        // TODO где мы вообще нашли такие эндпойнты ?
        return (web) -> web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}
