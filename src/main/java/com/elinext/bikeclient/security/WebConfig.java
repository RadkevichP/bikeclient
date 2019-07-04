package com.elinext.bikeclient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ISLogoutHandler isLogoutHandler;

    @Value("${logout-success-uri}")
    private String logoutSuccessUri;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Client()
                .and().oauth2Login();
    }
}