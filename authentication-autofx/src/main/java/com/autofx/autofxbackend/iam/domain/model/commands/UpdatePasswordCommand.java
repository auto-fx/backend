package com.autofx.autofxbackend.iam.domain.model.commands;

public record UpdatePasswordCommand(String currentPassword, String newPassword) {
}