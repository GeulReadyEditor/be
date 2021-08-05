package com.encore.backend.security;

import com.encore.backend.service.UserService;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder encoder;
    private Environment env;

    public WebSecurity(UserService userService, BCryptPasswordEncoder encoder, Environment env) {
        this.userService = userService;
        this.encoder = encoder;
        this.env = env;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        // http.authorizeRequests(ExpressionInterceptUrlRegistry ->
        // ExpressionInterceptUrlRegistry
        // .antMatchers(HttpMethod.GET,
        // "/board").permitAll().anyRequest().authenticated()).antMatcher("/users")
        // .oauth2ResourceServer().jwt();
        // http.antMatcher("/**").oauth2ResourceServer().jwt();

        // http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
        // .antMatchers(HttpMethod.GET,
        // "/board").permitAll().anyRequest().authenticated();
        http.headers().frameOptions().disable();
        // http.addFilter(getAuthenticationFilter());
        // http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // private AuthenticationFilter getAuthenticationFilter() throws Exception {
    // AuthenticationFilter authenticationFilter = new
    // AuthenticationFilter(authenticationManager(), userService, env);
    // return authenticationFilter;
    // }

}
