package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;


import com.autofx.autofxbackend.iam.domain.model.commands.SignUpCommand;
import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.PhoneNumber;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.SignUpResource;

import java.util.ArrayList;


public class SignUpCommandFromResourceAssembler {

    public static SignUpCommand toCommandFromResource(SignUpResource resource){
        var roles = resource.roles() != null ? resource.roles().stream().map(
                name -> Role.toRoleFromName(name)).toList(): new ArrayList<Role>();

        return new SignUpCommand(new Name(resource.name().firstName(), resource.name().lastName()), new EmailAddress(resource.emailAddress().address())
                , new PhoneNumber(resource.phoneNumber().countryCode(), resource.phoneNumber().number()), resource.password(), roles);
    }
}
