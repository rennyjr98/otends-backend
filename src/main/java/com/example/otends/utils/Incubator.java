package com.example.otends.utils;

import com.example.otends.entities.OThread;
import com.example.otends.entities.RawDataCfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Incubator {
    public static int SIZE = 3;
    public static int ON_LIFE = 0;

    public int MEMORYWIDTH = 100;

    private List<OThread> othreads;
    public Incubator() {
        this.othreads = new ArrayList<>();
    }

    public static void build(int...cfg) {
        SIZE = (cfg.length > 0) ? cfg[0] : SIZE;
        ON_LIFE = (cfg.length > 1) ? cfg[1] : ON_LIFE;
    }

    public int size() {
        return this.othreads.size();
    }

    public List<OThread> getOthreads() {
        return this.othreads;
    }

    public boolean add(OThread othread) {
        if(SIZE > this.othreads.size()) {
            this.othreads.add(othread);
            return true;
        }
        return false;
    }

    public void execute() {
        for(OThread othread: othreads) {
            if(othread != null) {
                othread.load();
                if (othread.state()) {
                    RawDataCfg cfg = othread.getCfg();
                    List<List<String>> data = DataParser.getData(cfg, othread.getId());
                    if (data != null) {
                        double[][] renderedData = DataParser.renderData(data, cfg);
                        renderedData = DataParser.renderOverOutput(renderedData, cfg);
                        for (double[] set : renderedData) {
                            double[] tests = Arrays.copyOfRange(set, 0, cfg.inputRows);
                            double[] targets = Arrays.copyOfRange(set, cfg.inputRows, set.length);
                            othread.train(tests, targets);
                        }
                        othread.save();
                    }
                }
            }
        }
    }


}
