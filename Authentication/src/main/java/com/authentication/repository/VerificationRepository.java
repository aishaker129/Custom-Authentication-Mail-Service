package com.authentication.repository;

import com.authentication.entity.User;
import com.authentication.entity.VerificationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE VerificationToken t SET t.used = true WHERE t.user = :user")
    void invalidateAllByUser(@Param("user") User user);
}
