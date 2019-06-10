package com.elinext.bikeclient.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ISLogoutHandler extends SecurityContextLogoutHandler {

    private final RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(ISLogoutHandler.class);

    public ISLogoutHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        super.logout(request, response, authentication);

        propagateLogoutToIS((OidcUser) authentication.getPrincipal());
        log.info("session invalidated");
    }

    private void propagateLogoutToIS(OidcUser user) {

        String endSessionEndpoint = "http://212.98.165.50:11084/connect/endsession";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(endSessionEndpoint)
                .queryParam("id_token_hint", user.getIdToken().getTokenValue());
        log.info(builder.toUriString());
        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            log.info(logoutResponse.toString());
            log.info("Sucessfully logged out in IS");
        } else {
            log.info(logoutResponse.toString());
            log.info("Logout from IS failed");
        }

    }
}
