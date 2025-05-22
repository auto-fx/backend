package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;

import com.autofx.autofxbackend.iam.domain.model.commands.UpdatePasswordCommand;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.UpdatePasswordResource;

public class UpdatePasswordCommandFromResourceAssembler {

    public static UpdatePasswordCommand toCommandFromResource(UpdatePasswordResource resource) {
        return new UpdatePasswordCommand(resource.currentPassword(), resource.newPassword());
    }

}
