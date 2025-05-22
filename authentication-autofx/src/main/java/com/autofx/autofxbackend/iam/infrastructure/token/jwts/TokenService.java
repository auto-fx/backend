package com.autofx.autofxbackend.iam.infrastructure.token.jwts;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {

    String generateToken(String username);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);

    String getBearerTokenFrom(HttpServletRequest token);
}
