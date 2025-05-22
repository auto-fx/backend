package com.autofx.autofxbackend.iam.domain.services;

import com.autofx.autofxbackend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {

    void execute(SeedRolesCommand command);

}
