package com.elinext.bikeclient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ISLogoutHandler isLogoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().logout()
                .addLogoutHandler(isLogoutHandler)
                .logoutSuccessUrl("http://212.98.165.50:11084/Account/Logout")
                .and().oauth2Client()
                .and().oauth2Login();
    }
}