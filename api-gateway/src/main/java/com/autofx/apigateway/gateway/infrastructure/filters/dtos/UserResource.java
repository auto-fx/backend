package com.autofx.apigateway.gateway.infrastructure.filters.dtos;


import com.autofx.apigateway.gateway.infrastructure.filters.dtos.valueobjects.EmailAddress;
import com.autofx.apigateway.gateway.infrastructure.filters.dtos.valueobjects.Name;
import com.autofx.apigateway.gateway.infrastructure.filters.dtos.valueobjects.PhoneNumber;

import java.util.List;

public record UserResource(Long id, Name name, PhoneNumber phoneNumber, EmailAddress emailAddress, List<String> roles){}

