package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;


import com.autofx.autofxbackend.iam.domain.model.commands.SignInCommand;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource){
        return new SignInCommand(new EmailAddress(resource.email()), resource.password());
    }
}
