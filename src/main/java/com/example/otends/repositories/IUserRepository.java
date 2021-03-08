package com.example.otends.repositories;

import com.example.otends.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM user u WHERE u.email = ?1 AND u.password = ?2", nativeQuery = true)
    Optional<User> findByLogin(String email, String password);
}
