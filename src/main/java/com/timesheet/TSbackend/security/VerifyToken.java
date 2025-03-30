package com.timesheet.TSbackend.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class VerifyToken extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            // No token provided; continue without setting authentication
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7); // Remove "Bearer " prefix

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            // Create an authentication object with the Firebase UID as the principal.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(decodedToken.getUid(), null, new ArrayList<>());
            // Optionally, you could load more details (e.g., roles) from your database here
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (FirebaseAuthException e) {
            // If the token is invalid, send an unauthorized error response.
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid  token");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
