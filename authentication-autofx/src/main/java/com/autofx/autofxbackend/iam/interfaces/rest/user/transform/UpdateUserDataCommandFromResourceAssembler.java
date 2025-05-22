package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;

import com.autofx.autofxbackend.iam.domain.model.commands.UpdateUserDataCommand;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.UpdateUserDataResource;

public class UpdateUserDataCommandFromResourceAssembler {
    public static UpdateUserDataCommand toCommandFromResource( UpdateUserDataResource resource) {
        return new UpdateUserDataCommand(new Name(resource.firstName(), resource.lastName()), resource.phoneNumber());
    }
}
