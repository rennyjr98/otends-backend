package com.example.otends.middleware.api;

import com.example.otends.middleware.OThreadService;
import com.example.otends.utils.Farm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FarmService extends Thread {
    private final OThreadService oThreadService;

    private boolean isWorking = true;
    private List<Farm> farms;
    @Autowired
    public FarmService(OThreadService oThreadService) {
        this.farms = new ArrayList<>();
        this.oThreadService = oThreadService;
    }

    public List<Farm> getFarms() {
        return this.farms;
    }

    private void execute() {
        for(Farm farm: this.farms) {
            if(!farm.isAlive()) {
                farm.start();
            }
        }
    }

    private void need() {
        if(this.farms.size() < 3) {
            this.farms.add(new Farm(this.oThreadService));
        }
    }

    @Override
    public void run() {
        while(isWorking) {
            try {
                need();
                execute();
                Thread.sleep(36000000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
