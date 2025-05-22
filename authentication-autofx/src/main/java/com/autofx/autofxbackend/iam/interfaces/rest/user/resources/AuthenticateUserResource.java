package com.autofx.autofxbackend.iam.interfaces.rest.user.resources;

public record AuthenticateUserResource(Long id, String email, String token) {
}
