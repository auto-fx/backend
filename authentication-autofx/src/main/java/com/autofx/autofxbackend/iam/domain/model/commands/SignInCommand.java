package com.autofx.autofxbackend.iam.domain.model.commands;

import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;

public record SignInCommand(EmailAddress email, String password) {
}
