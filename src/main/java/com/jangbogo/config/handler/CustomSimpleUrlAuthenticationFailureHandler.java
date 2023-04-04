package com.jangbogo.config.handler;

import com.jangbogo.config.security.util.CustomCookie;
import com.jangbogo.repository.auth.CustomAuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jangbogo.repository.auth.CustomAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@RequiredArgsConstructor
@Component
public class CustomSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CustomCookie.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        customAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
