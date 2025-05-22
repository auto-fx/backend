package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;

import com.autofx.autofxbackend.iam.domain.model.commands.SaveTokenInCookieCommand;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.SaveTokenInCookieResource;

public class SaveTokenInCookieCommandFromResourceAssembler {

    public static SaveTokenInCookieCommand toCommandFromResource(SaveTokenInCookieResource resource){
        return new SaveTokenInCookieCommand(resource.token());
    }

}
