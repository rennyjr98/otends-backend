package com.example.otends.repositories;

import com.example.otends.entities.RawDataCfg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRawDataCfgRepository extends JpaRepository<RawDataCfg, UUID> {
}
