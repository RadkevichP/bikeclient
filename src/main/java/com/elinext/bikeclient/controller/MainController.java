package com.elinext.bikeclient.controller;

import com.elinext.bikeclient.model.Bike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final Logger log = LoggerFactory.getLogger(MainController.class);
    private final String ROLE_CLAIM = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role";
    private static final String MANAGER_ROLE = "MANAGER";
    private static final String ADMIN_ROLE = "ADMIN";


    public MainController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @RequestMapping("/")
    public String index(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient auth2AuthorizedClient = this.getAuthorizedClient(authentication);
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("clientName", auth2AuthorizedClient.getClientRegistration().getClientName());
        return "index";
    }

    @RequestMapping("/managerpage")
    @PreAuthorize("authentication.getPrincipal().getAttributes()" +
            ".containsKey('http://schemas.microsoft.com/ws/2008/06/identity/claims/role')")
    public String managerPage(Model model, OAuth2AuthenticationToken authentication) {
        Map userAttributes = authentication.getPrincipal().getAttributes();
        List<String> roleClaims = (List<String>) authentication.getPrincipal().getAttributes().get(ROLE_CLAIM);
        if (roleClaims.contains(MANAGER_ROLE)) {
            model.addAttribute("userAttributes", userAttributes);
            return "managerpage";
        } else  throw new AccessDeniedException("You shall not pass!");
    }

    @RequestMapping("/adminpage")
    @PreAuthorize("authentication.getPrincipal().getAttributes()" +
            ".containsKey('http://schemas.microsoft.com/ws/2008/06/identity/claims/role')")
    public String adminPage(Model model, OAuth2AuthenticationToken authentication) {
        Map userAttributes = authentication.getPrincipal().getAttributes();
        List<String> roleClaims = (List<String>) authentication.getPrincipal().getAttributes().get(ROLE_CLAIM);
        if (roleClaims.contains(ADMIN_ROLE)) {
            model.addAttribute("userAttributes", userAttributes);
            return "adminpage";
        } else throw new AccessDeniedException("You shall not pass!");
    }

    @RequestMapping("/userinfo")
    @PreAuthorize("isAuthenticated()")
    public String userinfo(Model model, OAuth2AuthenticationToken authentication) {
        Map userAttributes = authentication.getPrincipal().getAttributes();
        model.addAttribute("userAttributes", userAttributes);
        return "userinfo";
    }

    @RequestMapping("/bikes")
    public String bikes(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        log.info(authorizedClient.getAccessToken().getTokenValue());
        List<Bike> bikes = WebClient.builder()
                .filter(oauth2Credentials(authorizedClient))
                .filter(logRequest())
                .build()
                .get()
                .uri("localhost:8097/api/v1/bikes")
                .retrieve()
                .bodyToFlux(Bike.class)
                .collectList()
                .block();
        model.addAttribute("bikes", bikes);
        bikes.forEach(b -> log.info(b.toString()));
        return "bikes";
    }


    private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Bearer " + authorizedClient.getAccessToken().getTokenValue())
                            .build();
                    return Mono.just(authorizedRequest);
                }
        );
    }


    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }

}
