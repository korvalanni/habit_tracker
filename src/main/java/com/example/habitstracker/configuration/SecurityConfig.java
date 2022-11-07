
package com.example.habitstracker.configuration;

import com.example.habitstracker.Constants;
import com.example.habitstracker.filters.ExceptionHandlerFilter;
import com.example.habitstracker.security.JWTAuthenticationFilter;
import com.example.habitstracker.security.JWTLoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            ExceptionHandlerFilter exceptionHandlerFilter,
            JWTLoginFilter jwtLoginFilter,
            JWTAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        {
            /**
             * На удивление в доке {@link GenericFilterBean} написано, что фильтры не загружают свой контекст и не
             * хранятся в контексте.
             */
            httpSecurity
                    .csrf().disable() // https://stackoverflow.com/questions/52363487/what-is-the-reason-to-disable-csrf-in-spring-boot-web-application https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B6%D1%81%D0%B0%D0%B9%D1%82%D0%BE%D0%B2%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D0%B4%D0%B5%D0%BB%D0%BA%D0%B0_%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81%D0%B0
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, Constants.API.LOGIN, "/auth/registration").permitAll()
                    .antMatchers(HttpMethod.GET,
                            "/swagger-ui/**",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/v2/api-docs",
                            "/webjars/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        }
    }
}