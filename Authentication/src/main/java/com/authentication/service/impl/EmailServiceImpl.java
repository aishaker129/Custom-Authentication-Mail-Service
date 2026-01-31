package com.authentication.service.impl;

import com.authentication.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sentVerificationMail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verify your account.");

        String link = "http://localhost:8080/api/verify?token=" + token;

        message.setText("""
                Click the link below to verify your account:
                %s
                
                Link expires in 10 minutes.
                """.formatted(link));

        javaMailSender.send(message);
    }
}
