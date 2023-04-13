package com.sloth.authentication.filter;

import com.sloth.authentication.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    /* 토큰 추출 */
    private String getToken(HttpServletRequest request) {

        // Request Header: authorization 추출
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // JWT 토큰 존재
        if (authorization != null && authorization.startsWith("bearer ")) {

            return authorization.substring(7);
        }
        // 토큰 없을 시
        return null;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);
        Authentication authentication = authenticationService.authenticate(token);

        if (authentication != null) {

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
