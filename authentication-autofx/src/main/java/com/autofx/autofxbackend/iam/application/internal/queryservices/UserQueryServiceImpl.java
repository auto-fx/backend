package com.autofx.autofxbackend.iam.application.internal.queryservices;

import com.autofx.autofxbackend.iam.domain.model.aggregates.User;
import com.autofx.autofxbackend.iam.domain.model.queries.GetUserByJwtQuery;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.services.UserQueryService;
import com.autofx.autofxbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.autofx.autofxbackend.iam.infrastructure.token.jwts.TokenService;
import com.autofx.autofxbackend.iam.infrastructure.token.jwts.services.TokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserQueryServiceImpl.class);
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public Optional<User> execute(GetUserByJwtQuery query, HttpServletRequest request) {
        var jwt = tokenService.getBearerTokenFrom(request);
        if (jwt.isEmpty() || jwt == null || jwt == ""){
            throw new RuntimeException("JWT not found");
        }
        var user = userRepository.findByEmail(new EmailAddress(tokenService.getUsernameFromToken(jwt))).orElseThrow(() -> new RuntimeException("User not found"));
        return Optional.of(user);
    }
}
