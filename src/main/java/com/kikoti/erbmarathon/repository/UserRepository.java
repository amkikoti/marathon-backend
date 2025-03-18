package com.kikoti.erbmarathon.repository;
import com.kikoti.erbmarathon.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kikoti.erbmarathon.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findUserByEmailAndRememberCode(String email, String confirmationCode);
    Optional<User> findByIdAndStatusAndArchive(Long userId, UserStatus status, int archive);

    Optional<User> findByIdAndStatus(Long id, UserStatus userStatus);
}