package com.example.otends.middleware;

import com.example.otends.dao.skeletons.OThreadDao;
import com.example.otends.entities.OThread;
import com.example.otends.utils.DataParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OThreadService {
    private final OThreadDao oThreadDao;
    private final ObjectMapper _jsonMapper;
    @Autowired
    public OThreadService(@Qualifier("OThreadDaoService")OThreadDao othread) {
        this.oThreadDao = othread;
        this._jsonMapper = new ObjectMapper();
    }

    public int add(OThread othread) {
        if(othread.getName() != null) {
            if(othread.getName().length() > 4) {
                boolean isInRange = othread.getCfg().inputRows >= 2;
                int inputLabelsSize = othread.getCfg().inputLabels.size();
                boolean inputLabelsEqualsRange = inputLabelsSize == othread.getCfg().inputRows;

                int outputLabelSize = othread.getCfg().outputLabels.size();
                boolean outpuLabelsEqualsRange = outputLabelSize == othread.getCfg().outputRows;

                if(isInRange && inputLabelsEqualsRange && outpuLabelsEqualsRange) {
                    return oThreadDao.add(othread);
                }
            }
        }
        return 0;
    }

    public int addFood(List<List<String>> food, UUID id) {
        try {
            OThread othread = get(id).orElse(null);
            if (othread != null) {
                return (writeFoodFile(food, othread.getId())) ? 1: 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean writeFoodFile(List<List<String>> food, UUID id) throws IOException {
        List<List<String>> currentFood = DataParser.getDataFromSystem(id);

        boolean exists = currentFood != null;
        boolean formatted = (exists) ? currentFood.get(0).size() == food.get(0).size() : false;
        if(exists && formatted) {
            for (List<String> nFood : food) {
                currentFood.add(nFood);
            }
        } else if(!exists) {
            currentFood = food;
        } else {
            return false;
        }

        this.oThreadDao.writeFood(currentFood, id);
        return true;
    }

    public List<OThread> getAll() {
        return this.oThreadDao.getAll();
    }

    public Optional<OThread> get(UUID id) {
        return this.oThreadDao.get(id);
    }

    public OThread getRandom() {
        return this.oThreadDao.getRandom();
    }

    public List<OThread> findByUser(UUID userId) {
        return this.oThreadDao.findByUser(userId);
    }
}
