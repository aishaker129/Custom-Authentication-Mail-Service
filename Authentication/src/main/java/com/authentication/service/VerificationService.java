package com.authentication.service;

import com.authentication.dto.response.ApiResponse;
import com.authentication.entity.User;

public interface VerificationService {
    void sentVerificationEmail(User user);

    ApiResponse<Void> verify(String token);
}
