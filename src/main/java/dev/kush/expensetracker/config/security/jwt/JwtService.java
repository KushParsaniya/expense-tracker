package dev.kush.expensetracker.config.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username);

    String extractUserName(String token);

    Boolean validateToken(String token, UserDetails userDetails);
}
