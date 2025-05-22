package com.autofx.autofxbackend.iam.infrastructure.token.jwts.services;

import com.autofx.autofxbackend.iam.infrastructure.token.jwts.BearerTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class TokenServiceImpl implements BearerTokenService {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String AUTHORIZATION_PARAMETER_NAME = "Authorization";

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    private static final int TOKEN_BEGIN_INDEX = 7;

    private static final String PREFS_NAME = "MyEncryptedPrefs";

    private static final String KEY_JWT = "jwt_token";

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("${authorization.jwt.expiration.days}")
    private int expirationDays;

    @Override
    public String generateToken(String username) {
        return buildTokenWithDefaultParameters(username);
    }

    @Override
    public String generateToken(Authentication authentication) {
        return buildTokenWithDefaultParameters(authentication.getName());
    }

    private String buildTokenWithDefaultParameters(String username){
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        var key = getSigninKey();
        return Jwts.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSigninKey(){//me retorna el SecretKey en bits
        var keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override                                                                           //con getSubject puedo obtener el username del token
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token);
            LOGGER.info("Token is valid");
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JSON Web Token Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JSON Web Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JSON Web Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JSON Web Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JSON Web Token claims string is empty: {}", e.getMessage());
        }
        return false;
    }



    @Override
    public String getBearerTokenFrom(HttpServletRequest token) {
        String parameter = token.getHeader(AUTHORIZATION_PARAMETER_NAME);
        if (isBearerTokenIn(parameter)){
            return parameter.substring(TOKEN_BEGIN_INDEX);
        }
        LOGGER.info("Authorization header is missing or does not contain Bearer token");
        return null;
    }

    private boolean isBearerTokenIn(String authorizationParameter) {
        return authorizationParameter != null && authorizationParameter.startsWith(BEARER_TOKEN_PREFIX);
    }

    private boolean isTokenPresentIn(String authorizationParameter) {
        return StringUtils.hasText(authorizationParameter);
    }

//    public static String getJwtFromCookie(HttpServletRequest request) {
//        var cookies = request.getCookies();
//        if (cookies != null) {
//            for (var cookie : cookies) {
//                if ("JWT".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
//
//    public static void saveJwtInCookie(HttpServletResponse response, String token){
//        Cookie cookie = new Cookie("JWT", token);
//        cookie.setHttpOnly(true);//cambiar a true en producción
//        cookie.setSecure(true);//cambiar a true en producción
//        cookie.setPath("/");
//        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 días
//        response.addCookie(cookie);
//    }

}
