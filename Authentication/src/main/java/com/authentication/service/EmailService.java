package com.authentication.service;

public interface EmailService {
    void sentVerificationMail(String email, String token);
}
