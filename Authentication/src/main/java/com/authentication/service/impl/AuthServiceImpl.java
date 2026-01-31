package com.authentication.service.impl;

import com.authentication.dto.request.LoginRequest;
import com.authentication.dto.request.RegisterRequest;
import com.authentication.dto.response.ApiResponse;
import com.authentication.entity.User;
import com.authentication.enums.Role;
import com.authentication.repository.UserRepository;
import com.authentication.service.AuthService;
import com.authentication.service.JwtService;
import com.authentication.service.VerificationService;
import com.authentication.util.JwtProperties;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    @Override
    public ApiResponse<Void> createUser(RegisterRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new EntityExistsException("Email already exists.");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);
        verificationService.sentVerificationEmail(user);

        ApiResponse apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.toString(),
                201,
                "Sent verification mail to complete register",
                false,
                null
        );
        return apiResponse;
    }

    @Override
    public ApiResponse<Void> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password())
        );

        User user = (User) authentication.getPrincipal();

        if(!user.getVerified()){
            verificationService.sentVerificationEmail(user);
            throw  new RuntimeException("Account not verified. New email sent.");
        }
        String accessToken = jwtService.generateToken((UserDetails) user);

        ApiResponse apiResponse = new ApiResponse<>(
                HttpStatus.OK.toString(),
                200,
                "Account login successful.",
                false,
                accessToken
        );
        return apiResponse;
    }
}
