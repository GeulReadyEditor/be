package com.encore.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.authorizeRequests(ExpressionInterceptUrlRegistry -> ExpressionInterceptUrlRegistry
                .antMatchers(HttpMethod.GET, "/board/count").permitAll().antMatchers(HttpMethod.GET, "/board")
                .permitAll().antMatchers(HttpMethod.POST, "/users").permitAll().antMatchers(HttpMethod.GET, "/users/**")
                .permitAll().antMatchers(HttpMethod.GET, "/comment").permitAll().anyRequest().authenticated())
                .antMatcher("/**").oauth2ResourceServer().jwt();
        http.headers().frameOptions().disable();
    }
}
