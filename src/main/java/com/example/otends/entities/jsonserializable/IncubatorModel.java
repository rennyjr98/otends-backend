package com.example.otends.entities.jsonserializable;

import com.example.otends.entities.OThread;

import java.util.ArrayList;
import java.util.List;

public class IncubatorModel {
    public static int SIZE = 3;
    public static int ON_LIFE = 0;

    public int MEMORYWIDTH = 100;

    public List<OThreadModel> othreads;
    public IncubatorModel() {
        this.othreads = new ArrayList<>();
    }
}
