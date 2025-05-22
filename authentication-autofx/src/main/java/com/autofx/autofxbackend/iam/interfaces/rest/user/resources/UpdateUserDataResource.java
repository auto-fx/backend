package com.autofx.autofxbackend.iam.interfaces.rest.user.resources;

import com.autofx.autofxbackend.iam.domain.model.valueobjects.PhoneNumber;

public record UpdateUserDataResource(String firstName, String lastName, PhoneNumber phoneNumber) {
}
