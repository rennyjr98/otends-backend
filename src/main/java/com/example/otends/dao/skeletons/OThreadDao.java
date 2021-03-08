package com.example.otends.dao.skeletons;

import com.example.otends.entities.OThread;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface OThreadDao extends EntityDao {
    void writeFood(List<List<String>> food, UUID id) throws IOException;
    OThread getRandom();
    List<OThread> findByUser(UUID id);
}
