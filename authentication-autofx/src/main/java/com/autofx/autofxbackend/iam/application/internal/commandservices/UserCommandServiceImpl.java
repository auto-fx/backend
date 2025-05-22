package com.autofx.autofxbackend.iam.application.internal.commandservices;

import com.autofx.autofxbackend.iam.domain.model.aggregates.User;
import com.autofx.autofxbackend.iam.domain.model.commands.*;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.services.UserCommandService;
import com.autofx.autofxbackend.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import com.autofx.autofxbackend.iam.infrastructure.mail.MailService;
import com.autofx.autofxbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.autofx.autofxbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.autofx.autofxbackend.iam.infrastructure.token.jwts.TokenService;
import com.autofx.autofxbackend.iam.infrastructure.token.jwts.services.TokenServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptHashingService bcryptHashingService;

    private final TokenService tokenService;

//    private final MailService mailService;

    private final TemplateEngine templateEngine;
    private final TokenServiceImpl tokenServiceImpl;



    @Override
    public Optional<User> execute(SignUpCommand command) {
        if (userRepository.existsByEmail(command.emailAddress())) {
            throw new RuntimeException("User already exists with this email address");
        }
        var roles = command.roles().stream().map(role -> {
           return roleRepository.findByName(role.getName()).orElseThrow(()-> new RuntimeException("Role name not found"));
        }).toList();
        var user = new User(command.name().firstName(), command.name().lastName(), bcryptHashingService.encode(command.password()),
                roles, command.phoneNumber().countryCode(), command.phoneNumber().number(), command.emailAddress().address());
        userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<ImmutablePair<User, String>> execute(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        if (!bcryptHashingService.matches(command.password(), user.get().getPassword())){
            throw new RuntimeException("Invalid password");
        }
        var token = tokenService.generateToken(user.get().getEmail().address());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public boolean execute(UpdatePasswordCommand command, HttpServletRequest request) {
        var email = tokenService.getUsernameFromToken(tokenService.getBearerTokenFrom(request));
        var user = userRepository.findByEmail(new EmailAddress(email));
        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        if (!bcryptHashingService.matches(command.currentPassword(), user.get().getPassword())){
            throw new RuntimeException("Invalid password");
        }
        user.get().setPassword(bcryptHashingService.encode(command.newPassword()));
        userRepository.save(user.get());
        return true;
    }

    @Override
    public boolean execute(UpdateUserDataCommand command, HttpServletRequest request) {
        var email = tokenService.getUsernameFromToken(tokenService.getBearerTokenFrom(request));
        var user = userRepository.findByEmail(new EmailAddress(email));
        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        user.get().setName(command.name());
        user.get().setPhoneNumber(command.phoneNumber());
        userRepository.save(user.get());
        return true;
    }

//    @Override
//    public String execute(SendEmailRecoverAccountCommand command) throws MessagingException {
//        var email = command.email();
//        if (!userRepository.existsByEmail(email)){
//            throw new RuntimeException("User not found, the email hasn't been sent");
//        }
//
//        var token = tokenService.generateToken(email.address());
//        var verifyAccount = "http://localhost:9090/api/v1/users/verify-account?token=" + token;
//
//        Context context = new Context();
//
//        context.setVariable("verifyAccount", verifyAccount);
//
//        String htmlMessageContent = templateEngine.process("recover-email", context);
//
//        mailService.sendEmail(email.address(), "SquidZ recover account", htmlMessageContent);
//
//        return "The email has been sent";
//    }
//
//    @Override
//    public void execute(SaveTokenInCookieCommand command, HttpServletRequest request, HttpServletResponse response) {
//       if (!tokenServiceImpl.validateToken(command.token())){
//           throw new RuntimeException("token not valid");
//       }
//       var userAgent = request.getHeader(HttpHeaders.USER_AGENT);
//       if (userAgent != null && !userAgent.contains("Android") && !userAgent.contains("iPhone") && !userAgent.contains("iPad")) {
//           TokenServiceImpl.saveJwtInCookie(response, command.token());
//       }
//    }
//
//    @Override
//    public String execute(UpdatePasswordRecoverAccountCommand command, HttpServletRequest request) {
//        var jwt = TokenServiceImpl.getJwtFromCookie(request);
//        var user = userRepository.findByEmail(new EmailAddress(tokenService.getUsernameFromToken(jwt))).orElseThrow(() -> new RuntimeException("User not found"));
//        if ( !(command.newPassword().equals(command.repeatPassword())) ){
//            throw new RuntimeException("Passwords do not match");
//        }
//        user.setPassword(bcryptHashingService.encode(command.newPassword()));
//        userRepository.save(user);
//        return "Password updated successfully";
//    }


}
