package com.autofx.autofxbackend.iam.domain.model.commands;

import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;

public record SendEmailRecoverAccountCommand(EmailAddress email) {
}
