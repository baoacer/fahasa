package com.whilewework.fahasa.repository;

import com.whilewework.fahasa.entity.User;
import com.whilewework.fahasa.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole userRole);
}