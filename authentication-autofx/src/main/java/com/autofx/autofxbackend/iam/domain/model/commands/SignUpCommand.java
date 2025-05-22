package com.autofx.autofxbackend.iam.domain.model.commands;


import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.PhoneNumber;

import java.util.List;

public record SignUpCommand(Name name, EmailAddress emailAddress, PhoneNumber phoneNumber, String password, List<Role> roles) {
}
