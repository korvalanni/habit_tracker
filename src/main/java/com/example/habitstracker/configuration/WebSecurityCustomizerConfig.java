package com.example.habitstracker.configuration;

import com.example.habitstracker.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class WebSecurityCustomizerConfig {
    @Bean
    public WebSecurityCustomizer webCustomizer()
    {
        // TODO где мы вообще нашли такие эндпойнты ?
        return (web) -> web.ignoring().antMatchers(Constants.Swagger.UI_ANY, Constants.Swagger.API_DOCS);
    }
}
