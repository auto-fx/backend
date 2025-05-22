package com.autofx.autofxbackend.iam.application.internal.commandservices;

import com.autofx.autofxbackend.iam.domain.model.commands.SeedRolesCommand;
import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Roles;
import com.autofx.autofxbackend.iam.domain.services.RoleCommandService;
import com.autofx.autofxbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@AllArgsConstructor
@Service
public class RoleCommandServiceImpl implements RoleCommandService {


    private final RoleRepository roleRepository;

    @Override
    public void execute(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if(!roleRepository.existsByName(role)){
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}
