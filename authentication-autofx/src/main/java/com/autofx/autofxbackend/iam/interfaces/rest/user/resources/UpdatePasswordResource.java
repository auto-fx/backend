package com.autofx.autofxbackend.iam.interfaces.rest.user.resources;

public record UpdatePasswordResource(String currentPassword, String newPassword) {
}
