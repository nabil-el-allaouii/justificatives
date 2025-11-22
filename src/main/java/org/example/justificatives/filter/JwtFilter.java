package org.example.justificatives.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.justificatives.security.JwtUtils;
import org.example.justificatives.service.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // STEP 1: Extract JWT token from Authorization header
        String jwt = parseJwt(request);
        // Example: "Bearer eyJhbGc..." → extract "eyJhbGc..."

        // STEP 2: Check if token exists and is valid
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

            // STEP 3: Extract email from token
            String email = jwtUtils.getUserNameFromJwtToken(jwt);

            // STEP 4: Load user from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // STEP 5: Create authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // STEP 6: Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // STEP 7: Continue the filter chain (pass request to next filter/controller)
        filterChain.doFilter(request, response);
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Remove "Bearer " prefix
        }

        return null;
    }
}
