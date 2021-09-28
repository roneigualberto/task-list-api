package com.ronei.tasklist.infra.security;

import com.example.tasklist.infrastructure.security.identity.Identity;
import com.example.tasklist.infrastructure.security.identity.IdentityService;
import com.example.tasklist.infrastructure.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {


    private final AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = getToken(request);

        authenticationService.authenticate(token);

        filterChain.doFilter(request, response);
    }


    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
}
