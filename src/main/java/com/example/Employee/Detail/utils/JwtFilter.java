package com.example.Employee.Detail.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * JwtFilter - extracts Bearer token from Authorization header and validates it.
 * If token is valid sets authentication in SecurityContext; otherwise responds 401.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final Logger logger = Logger.getLogger(JwtFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (token.isBlank()) {
                    logger.warning("Empty Bearer token");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Empty token");
                    return;
                }
                if (jwtUtil.validateJwtToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    var auth = new UsernamePasswordAuthenticationToken(email, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    logger.warning("Invalid or expired JWT token");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                    return;
                }
            }
            // continue filter chain
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            logger.severe("JwtFilter error: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
        }
    }
}
