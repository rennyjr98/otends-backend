package com.example.otends.utils;

import com.example.otends.entities.OThread;
import com.example.otends.middleware.OThreadService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Farm extends Thread {
    public static int FARMER_LIMIT = 10;
    public static int ON_HOLD_OTHREADS_LIMIT = 20;

    private int MANAGER_LIMIT = 2;
    @JsonIgnore
    private final OThreadService oThreadService;

    private int nManagers = 0;
    private boolean isOpen = true;

    private List<Farmer> farmers;
    private List<OThread> onHoldOThreads;
    public Farm(OThreadService oThreadService) {
        this.farmers = new ArrayList<>();
        this.onHoldOThreads = new ArrayList<>();
        this.oThreadService = oThreadService;
    }

    private void open() {
        for(Farmer farmer : this.farmers) {
            if(!farmer.isAlive()) {
                buildIncubator(farmer);
                farmer.start();
            }
        }
    }

    private void buildIncubator(Farmer farmer) {
        boolean added = farmer.asign(new Incubator());
        if(added) {
            for (int i = 0; i < Incubator.SIZE / 2; i++) {
                OThread othread = this.oThreadService.getRandom();
                farmer.farm(othread);
            }
        }
    }

    public List<Farmer> getFarmers() {
        return this.farmers;
    }

    private int needFarm() {
        int index = -1;
        for(Farmer farmer : this.farmers) {
            if(farmer.isManager() && farmer.farmSize() < Farmer.MANAGER_INCUBATOR_LIMIT) {
                return index+1;
            } else if(!farmer.isManager() && farmer.farmSize() < Farmer.EMPLOYEE_INCUBATOR_LIMIT) {
                return index+1;
            }
            index++;
        }
        return index;
    }

    private void addFarm(int index) {
        // TODO : Add real OThread
        this.farmers.get(index).farm(new OThread());
    }

    private boolean hireEmploye(boolean isManager) {
        boolean canHire = false;
        if(isManager) {
            if(this.nManagers < this.MANAGER_LIMIT) {
                this.farmers.add(new Farmer(isManager, this.oThreadService));
                canHire = true;
            }
        } else {
            if(this.farmers.size() < FARMER_LIMIT) {
                this.farmers.add(new Farmer(isManager, this.oThreadService));
                canHire = true;
            }
        }
        return canHire;
    }

    private boolean needHire() {
        boolean needIt = false;
        if(this.farmers.size() < FARMER_LIMIT) {
            if(this.onHoldOThreads.size() < ON_HOLD_OTHREADS_LIMIT) {
                needIt = true;
            }
        }
        return needIt;
    }

    @Override
    public void run() {
        while(this.isOpen) {
            try {
                if(needHire()) {
                    if(!hireEmploye(true)) {
                        hireEmploye(false);
                    }
                }

                int index = needFarm();
                if(index != -1) {
                    addFarm(index);
                }
                open();
                Thread.sleep(3600000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
