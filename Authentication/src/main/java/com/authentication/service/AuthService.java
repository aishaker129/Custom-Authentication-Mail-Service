package com.authentication.service;

import com.authentication.dto.request.LoginRequest;
import com.authentication.dto.request.RegisterRequest;
import com.authentication.dto.response.ApiResponse;
import jakarta.validation.Valid;

public interface AuthService {
    ApiResponse<Void> createUser(@Valid RegisterRequest request);

    ApiResponse<Void> login(@Valid LoginRequest request);
}
