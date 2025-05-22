package com.autofx.autofxbackend.iam.infrastructure.mail.service;

import com.autofx.autofxbackend.iam.infrastructure.mail.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//@Service
//public class MailServiceImpl implements MailService {
//
//    private final JavaMailSender mailSender;
//
//    private final String fromEmail;
//
//    public MailServiceImpl(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromEmail){
//        this.mailSender = mailSender;
//        this.fromEmail = fromEmail;
//    }
//
//    @Override
//    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//        helper.setTo(toEmail);
//        helper.setSubject(subject);
//        helper.setText(body, true);
//        helper.setFrom(this.fromEmail);
//
//        mailSender.send(message);
//    }
//}
