package com.autofx.autofxbackend.iam.interfaces.rest.user.resources;

public record UpdatePasswordRecoverAccountResource (String newPassword, String passwordRepeat){}
