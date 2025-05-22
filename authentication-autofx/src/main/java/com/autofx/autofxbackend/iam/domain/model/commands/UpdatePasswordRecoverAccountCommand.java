package com.autofx.autofxbackend.iam.domain.model.commands;

public record UpdatePasswordRecoverAccountCommand(String newPassword, String repeatPassword) {
}
