package com.example.habitstracker.configuration;

import com.example.habitstracker.repository.UserRepository;
import com.example.habitstracker.security.JWTAuthenticationFilter;
import com.example.habitstracker.security.JWTLoginFilter;
import com.example.habitstracker.security.TokenAuthenticationService;
import com.example.habitstracker.services.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// TODO
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AppUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    private final String jwtSecret;
    // TODO для единообразия и читаемости перенести все в конструктор
    // TODO запихнуть в TokenAuthenticationService
    @Value("${app.jwt.expire}")
    private long jwtExpire;

    @Autowired
    public SecurityConfig(AppUserDetailsService userDetailsService, UserRepository userRepository,
        @Value("${app.jwt.secret}") String jwtSecret)
    {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtSecret = jwtSecret;
    }
    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception
    {
        initTokenAuthenticationService();

        httpSecurity
                .csrf().disable() // https://stackoverflow.com/questions/52363487/what-is-the-reason-to-disable-csrf-in-spring-boot-web-application https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B6%D1%81%D0%B0%D0%B9%D1%82%D0%BE%D0%B2%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D0%B4%D0%B5%D0%BB%D0%BA%D0%B0_%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81%D0%B0
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                // TODO реализовать DI
                // TODO спрятать сервис
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), userRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private void initTokenAuthenticationService() {
        TokenAuthenticationService.setSECRET(jwtSecret);
        TokenAuthenticationService.setTokenExpiry(jwtExpire);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //toDo избавиться от deprecated метода
    // toDo реализовать auth manager
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
            .csrf().disable() // https://stackoverflow.com/questions/52363487/what-is-the-reason-to-disable-csrf-in-spring-boot-web-application https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B6%D1%81%D0%B0%D0%B9%D1%82%D0%BE%D0%B2%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D0%B4%D0%B5%D0%BB%D0%BA%D0%B0_%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81%D0%B0
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/login", "/registration").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), userRepository), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    {
        return authenticationConfiguration.getAuthenticationManager()
    }
    */

}
