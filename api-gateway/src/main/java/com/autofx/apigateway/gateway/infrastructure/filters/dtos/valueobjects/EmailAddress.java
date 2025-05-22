package com.autofx.apigateway.gateway.infrastructure.filters.dtos.valueobjects;

public record EmailAddress(String address) {
    public EmailAddress(){
        this(null);
    }
}
