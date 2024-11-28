
package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        log.debug("Authorization Header: {}", authHeader);

        final String jwtToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Authorization header is missing or does not start with 'Bearer '. Skipping filter.");
            chain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        log.debug("Extracted JWT Token: {}", jwtToken);

        try {
            username = jwtUtil.extractUsername(jwtToken);
            log.debug("Extracted Username: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.debug("Loaded UserDetails: {}", userDetails.getUsername());

                if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                    log.debug("JWT Token is valid for user: {}", username);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("User '{}' authenticated successfully.", username);
                } else {
                    log.warn("JWT Token is invalid for user: {}", username);
                }
            }
        } catch (Exception e) {
            log.error("Error processing JWT Token: {}", e.getMessage(), e);
        }

        chain.doFilter(request, response);
    }
}
