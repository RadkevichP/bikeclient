package com.elinext.bikeclient;

import com.elinext.bikeclient.security.UserFeignClientInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

@SpringBootApplication
@EnableFeignClients("com.elinext.bikeclient")
public class BikeclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikeclientApplication.class, args);
    }

    @Bean
    public RequestInterceptor getUserFeignClientInterceptor(OAuth2AuthorizedClientService clientService) {
        return new UserFeignClientInterceptor(clientService);
    }
}
