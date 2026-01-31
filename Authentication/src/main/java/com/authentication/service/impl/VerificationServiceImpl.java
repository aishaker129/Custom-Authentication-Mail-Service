package com.authentication.service.impl;

import com.authentication.dto.response.ApiResponse;
import com.authentication.entity.User;
import com.authentication.entity.VerificationToken;
import com.authentication.exception.TokenAlreadyExpiredException;
import com.authentication.repository.UserRepository;
import com.authentication.repository.VerificationRepository;
import com.authentication.service.EmailService;
import com.authentication.service.VerificationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public void sentVerificationEmail(User user) {
        if(user.getLastVerificationEmailSendAt() != null &&
                user.getLastVerificationEmailSendAt()
                        .plusMinutes(5)
                        .isAfter(LocalDateTime.now())){
            throw new RuntimeException("Please wait 5 minutes before retrying.");
        }

        verificationRepository.invalidateAllByUser(user);

        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpireAt(LocalDateTime.now().plusMinutes(10));

        verificationRepository.save(token);

        user.setLastVerificationEmailSendAt(LocalDateTime.now());
        userRepository.save(user);

        emailService.sentVerificationMail(user.getEmail(),token.getToken());

    }

    @Override
    public ApiResponse<Void> verify(String token) {
        VerificationToken verificationToken = verificationRepository.findByToken(token).orElseThrow(
                ()-> new EntityNotFoundException("Token not found.")
        );

        if(verificationToken.getUsed() || verificationToken.getExpireAt().isBefore(LocalDateTime.now())){
            throw new TokenAlreadyExpiredException("Token expired or already used");
        }
        User user = verificationToken.getUser();
        user.setVerified(true);
        verificationToken.setUsed(true);

        userRepository.save(user);
        verificationRepository.save(verificationToken);

        ApiResponse apiResponse = new ApiResponse<>(
                HttpStatus.OK.toString(),
                200,
                "This account verified.",
                false,
                null
        );
        return apiResponse;
    }
}
