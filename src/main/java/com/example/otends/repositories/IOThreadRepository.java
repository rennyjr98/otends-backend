package com.example.otends.repositories;

import com.example.otends.entities.OThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IOThreadRepository extends JpaRepository<OThread, UUID> {
    @Query(value = "SELECT * FROM othread o WHERE o.user_id = ?1", nativeQuery = true)
    List<OThread> findByUser(UUID id);
}
