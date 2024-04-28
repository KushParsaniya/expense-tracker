package dev.kush.expensetracker.config.security.jwt;

import dev.kush.expensetracker.constants.ErrorMessageConstants;
import dev.kush.expensetracker.services.api.SecuredMemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final SecuredMemberService securedMemberService;

    public JwtFilter(JwtService jwtService, SecuredMemberService securedMemberService) {
        this.jwtService = jwtService;
        this.securedMemberService = securedMemberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("x-jwt-token");
        String username = null;
        String jwt = null;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, "Invalid token", HttpStatus.FORBIDDEN.value());
            return;
        }
        jwt = authHeader.substring(7);
        try {
            username = jwtService.extractUserName(jwt);
        } catch (Exception e) {
            sendErrorResponse(response, ErrorMessageConstants.INVALID_TOKEN_SIGNATURE, HttpStatus.FORBIDDEN.value());
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = securedMemberService.loadUserByUsername(username);

            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(), null, userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                sendErrorResponse(response, ErrorMessageConstants.INVALID_TOKEN_SIGNATURE, HttpStatus.FORBIDDEN.value());
                return;
            }
        } else {
            sendErrorResponse(response, ErrorMessageConstants.INVALID_TOKEN_SIGNATURE, HttpStatus.FORBIDDEN.value());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        LocalDateTime timeStamp = LocalDateTime.now();
        String jsonResponse = String.format(
                """
                        {
                        "statusCode": %d,
                        "message": "%s",
                        "timestamp": "%s"
                        }
                        """
                , status, message, timeStamp);
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        final String servletPath = request.getServletPath();
        Set<String> nonFilteredPaths = new HashSet<>(Arrays.asList(
                "/swagger-ui.html", "/api/v1/members/sign-in", "/api/v1/members/conform", "/api/v1/members/sign-up"
        ));
        return nonFilteredPaths.contains(servletPath);
    }
}