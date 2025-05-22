package com.autofx.autofxbackend.iam.interfaces.rest.user.resources;

import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.PhoneNumber;

import java.util.List;

public record SignUpResource(Name name, EmailAddress emailAddress, PhoneNumber phoneNumber, String password, List<String> roles) {
}
