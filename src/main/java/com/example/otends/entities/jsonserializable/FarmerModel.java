package com.example.otends.entities.jsonserializable;

import java.util.ArrayList;
import java.util.List;

public class FarmerModel {
    public int MANAGER_INCUBATOR_LIMIT = 8;
    public int EMPLOYEE_INCUBATOR_LIMIT = 4;

    public boolean isManager = false;
    public boolean isWorking = true;
    public List<IncubatorModel> incubators;
    public FarmerModel() {
        this.incubators = new ArrayList<>();
    }
}
