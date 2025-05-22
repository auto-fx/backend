package com.autofx.autofxbackend.iam.infrastructure.persistence.jpa.repositories;

import com.autofx.autofxbackend.iam.domain.model.aggregates.User;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(Name name);

    Optional<User> findByEmail(EmailAddress emailAddress);

    boolean existsByEmail(EmailAddress emailAddress);

    boolean existsByName(Name name);
}
