package com.autofx.autofxbackend.iam.interfaces.rest.user.transform;


import com.autofx.autofxbackend.iam.domain.model.aggregates.User;
import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.AuthenticateUserResource;
import com.autofx.autofxbackend.iam.interfaces.rest.user.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user){
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getName(), user.getPhoneNumber(), user.getEmail(), roles);
    }

    public static AuthenticateUserResource toResourceFromEntityAndToken(User user, String token){
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new AuthenticateUserResource(user.getId(), user.getEmail().address(), token);
    }
}
