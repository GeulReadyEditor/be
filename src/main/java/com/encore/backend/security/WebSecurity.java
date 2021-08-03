package com.encore.backend.security;

import com.encore.backend.service.UserService;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder encoder;
    private Environment env;
    private JwtFilter jwtFilter;

    public WebSecurity(UserService userService, BCryptPasswordEncoder encoder, Environment env, JwtFilter jwtFilter) {
        this.userService = userService;
        this.encoder = encoder;
        this.env = env;
        // this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests(ExpressionInterceptUrlRegistry -> ExpressionInterceptUrlRegistry
                .antMatchers(HttpMethod.GET, "/users").permitAll().anyRequest().authenticated()).antMatcher("/**")
                .oauth2ResourceServer().jwt();

        // http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
        // .antMatchers(HttpMethod.GET,
        // "/board").permitAll().anyRequest().authenticated();
        http.headers().frameOptions().disable();
        // http.addFilter(getAuthenticationFilter());
        // http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, env);
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }
}
