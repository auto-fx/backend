package com.autofx.autofxbackend.iam.domain.model.commands;

import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.PhoneNumber;

public record UpdateUserDataCommand(Name name, PhoneNumber phoneNumber) {
}
