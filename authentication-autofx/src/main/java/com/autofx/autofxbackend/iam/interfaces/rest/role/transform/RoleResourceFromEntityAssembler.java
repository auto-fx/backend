package com.autofx.autofxbackend.iam.interfaces.rest.role.transform;


import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.interfaces.rest.role.resource.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role){
        return new RoleResource(role.getId(), role.getStringName());
    }
}
