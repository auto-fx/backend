package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;

import com.autofx.autofxbackend.iam.domain.model.commands.SendEmailRecoverAccountCommand;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.SendEmailRecoverAccountResource;

public class SendEmailRecoverAccountCommandFromrResourceAssembler {

    public static SendEmailRecoverAccountCommand toCommandFromResource(SendEmailRecoverAccountResource resource){
        return new SendEmailRecoverAccountCommand(new EmailAddress(resource.email()));
    }
}
