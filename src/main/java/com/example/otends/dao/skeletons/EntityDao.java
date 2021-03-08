package com.example.otends.dao.skeletons;

import com.example.otends.entities.models.OEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityDao {
    int add(OEntity entity);
    <T extends OEntity> List<T> getAll();
    <T extends OEntity> Optional<T> get(UUID id);
    int delete(UUID id);
    int update(UUID id, OEntity user);
}
