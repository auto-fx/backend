package com.autofx.autofxbackend.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;

@Embeddable
public record EmailAddress(@Column(name = "email") @Email String address) {
    public EmailAddress(){
        this(null);
    }
}
