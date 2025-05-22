package com.autofx.autofxbackend.iam.domain.services;

import com.autofx.autofxbackend.iam.domain.model.aggregates.User;
import com.autofx.autofxbackend.iam.domain.model.queries.GetUserByJwtQuery;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserQueryService {
    Optional<User> execute(GetUserByJwtQuery query, HttpServletRequest request);
}
