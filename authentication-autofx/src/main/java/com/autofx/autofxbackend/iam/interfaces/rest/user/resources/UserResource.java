package com.autofx.autofxbackend.iam.interfaces.rest.user.resources;

import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.PhoneNumber;

import java.util.List;

public record UserResource (Long id, Name name, PhoneNumber phoneNumber, EmailAddress emailAddress, List<String> roles){}

