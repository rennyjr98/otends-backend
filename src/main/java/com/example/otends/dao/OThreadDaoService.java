package com.example.otends.dao;

import com.example.otends.dao.skeletons.OThreadDao;
import com.example.otends.entities.OThread;
import com.example.otends.entities.models.OEntity;
import com.example.otends.repositories.IOThreadRepository;
import com.example.otends.repositories.IRawDataCfgRepository;
import com.example.otends.utils.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service("OThreadDaoService")
public class OThreadDaoService implements OThreadDao {
    @Autowired
    private IOThreadRepository _repositoryOThread;
    @Autowired
    private IRawDataCfgRepository _repositoryRawDataCfg;

    @Override
    public int add(OEntity entity) {
        OThread othread = (OThread) entity;
        _repositoryRawDataCfg.save(othread.getCfg());
        _repositoryOThread.save(othread);
        return 1;
    }

    @Override
    public void writeFood(List<List<String>> food, UUID id) throws IOException {
        File jsonFile = new File(OThread.BRAIN_BASEURL, "/food/" + id + ".json");
        DataParser.mapper.writeValue(jsonFile, food);
    }

    @Override
    public List<OThread> getAll() {
        return this._repositoryOThread.findAll();
    }

    @Override
    public Optional<OThread> get(UUID id) {
        return this._repositoryOThread.findById(id);
    }

    @Override
    public OThread getRandom() {
        List<OThread> all = getAll();
        if(all.size() > 0) {
            if(all.size() == 1) {
                return all.get(0);
            }
            Random rand = new Random();
            return all.get(rand.nextInt(all.size()));
        }
        return null;
    }

    @Override
    public List<OThread> findByUser(UUID id) {
        return this._repositoryOThread.findByUser(id);
    }

    @Override
    public int delete(UUID id) {
        return 0;
    }

    @Override
    public int update(UUID id, OEntity user) {
        return 0;
    }
}
