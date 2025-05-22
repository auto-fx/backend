package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;

import com.autofx.autofxbackend.iam.domain.model.commands.UpdatePasswordRecoverAccountCommand;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.UpdatePasswordRecoverAccountResource;

public class UpdatePasswordRecoverAccountCommandFromResourceAssembler {

    public static UpdatePasswordRecoverAccountCommand toCommandFromResource(UpdatePasswordRecoverAccountResource resource) {
        return new UpdatePasswordRecoverAccountCommand(resource.newPassword(), resource.passwordRepeat());
    }
}
