package com.example.otends.utils;

import com.example.otends.entities.OThread;
import com.example.otends.middleware.OThreadService;

import java.util.ArrayList;
import java.util.List;

public class Farmer extends Thread {
    public static int MANAGER_INCUBATOR_LIMIT = 8;
    public static int EMPLOYEE_INCUBATOR_LIMIT = 4;

    private boolean isManager = false;
    private boolean isWorking = true;
    private List<Incubator> incubators;
    private OThreadService oThreadService;
    public Farmer() {
        this.incubators = new ArrayList<>();
    }

    public Farmer(boolean isManager, OThreadService oThreadService) {
        this.isManager = isManager;
        this.incubators = new ArrayList<>();
        this.oThreadService = oThreadService;
    }

    public int farmSize() {
        return this.incubators.size();
    }

    public boolean isManager() {
        return isManager;
    }

    public List<Incubator> getIncubators() {
        return this.incubators;
    }

    public boolean asign(Incubator incubator) {
        boolean canAsign = false;
        if(isManager && MANAGER_INCUBATOR_LIMIT > this.incubators.size()) {
            this.incubators.add(incubator);
            canAsign = true;
        } else if(!isManager && EMPLOYEE_INCUBATOR_LIMIT > this.incubators.size()) {
            this.incubators.add(incubator);
            canAsign = true;
        }
        return canAsign;
    }

    public boolean farm(OThread othread) {
        boolean canFarm = false;
        for(Incubator incubator : this.incubators) {
            if(incubator.add(othread)) {
                canFarm = true;
            }
        }
        return canFarm;
    }

    public void finish() {
        this.isWorking = false;
    }

    @Override
    public void run() {
        while(this.isWorking) {
            try {
                for (Incubator incubator : this.incubators) {
                    if(Incubator.SIZE > incubator.size()) {
                        incubator.add(this.oThreadService.getRandom());
                    }
                    incubator.execute();
                }
                // 30000
                Thread.sleep(3000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
