package com.autofx.autofxbackend.iam.domain.services;


import com.autofx.autofxbackend.iam.domain.model.aggregates.User;
import com.autofx.autofxbackend.iam.domain.model.commands.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> execute(SignUpCommand command);

    Optional<ImmutablePair<User, String>> execute(SignInCommand command);

    boolean execute (UpdatePasswordCommand command, HttpServletRequest request);

    boolean execute (UpdateUserDataCommand command, HttpServletRequest request);

//    String execute (SendEmailRecoverAccountCommand command) throws MessagingException;
//
//    void execute(SaveTokenInCookieCommand command, HttpServletRequest request, HttpServletResponse response);
//
//    String execute (UpdatePasswordRecoverAccountCommand command, HttpServletRequest request);

}
