package com.elinext.bikeclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    @Value("${end-session-endpoint}")
    private String endSessionEndpoint;

    @RequestMapping("/signout")
    public RedirectView logout(HttpServletRequest request, Authentication authentication) {
        OidcUser user = (OidcUser) authentication.getPrincipal();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(endSessionEndpoint)
                .queryParam("id_token_hint", user.getIdToken().getTokenValue())
                .queryParam("post_logout_redirect_uri", "http://localhost:8082/");
        authentication.setAuthenticated(false);
        request.getSession().invalidate();

        return new RedirectView(builder.toUriString());

    }
}
