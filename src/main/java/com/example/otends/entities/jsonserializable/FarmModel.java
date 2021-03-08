package com.example.otends.entities.jsonserializable;

import com.example.otends.entities.OThread;

import java.util.ArrayList;
import java.util.List;

public class FarmModel {
    public int FARMER_LIMIT = 10;
    public int ON_HOLD_OTHREADS_LIMIT = 20;

    public int MANAGER_LIMIT = 2;

    public int nManagers = 0;
    public boolean isOpen = true;

    public List<FarmerModel> farmers;
    public List<OThread> onHoldOThreads;
    public FarmModel() {
        this.farmers = new ArrayList<>();
        this.onHoldOThreads = new ArrayList<>();
    }
}
