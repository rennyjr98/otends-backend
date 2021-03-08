package com.example.otends.controller;

import com.example.otends.entities.OThread;
import com.example.otends.entities.jsonserializable.OThreadModel;
import com.example.otends.entities.jsonserializable.PredictModel;
import com.example.otends.middleware.OThreadService;
import com.example.otends.utils.DataParser;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/othread")
@RestController
public class OThreadController {
    private final OThreadService oThreadService;
    private final ModelMapper _mapper;
    @Autowired
    public OThreadController(OThreadService oThreadService) {
        this.oThreadService = oThreadService;
        this._mapper = new ModelMapper();
        this._mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    @PostMapping
    public void add(@RequestBody OThreadModel oThreadModel) {
        OThread oThread = this._mapper.map(oThreadModel, OThread.class);
        oThread.createNN(oThreadModel.cfg.inputRows, 200, oThreadModel.cfg.outputRows);
        this.oThreadService.add(oThread);
        oThread.save();
    }

    @PostMapping("food/{id}")
    public void addFood(@RequestBody List<List<String>> food, @PathVariable UUID id) {
        this.oThreadService.addFood(food, id);
    }

    @PostMapping("predict")
    public PredictModel predict(@RequestBody PredictModel pModel) {
        OThread othread = this.oThreadService.get(pModel.othread.id).orElse(null);
        if(othread != null) {
            othread.load();
            List<List<String>> wrpCase = new ArrayList<>();
            wrpCase.add(pModel.pCase);
            double[][] testCase = DataParser.renderData(wrpCase, pModel.othread.cfg);
            double[] results =  othread.getNn().predict(testCase[0]);
            List<Double> cResults = new ArrayList<>();
            for(double data: results) {
                cResults.add(data);
            }
            pModel.results = cResults;
        }
        return pModel;
    }

    @GetMapping
    public List<OThread> getAll() {
        return this.oThreadService.getAll();
    }

    @GetMapping(path = "{id}")
    public OThread get(@PathVariable UUID id) {
        return this.oThreadService.get(id).orElse(null);
    }

    @GetMapping(path = "user/{id}")
    public List<OThread> getByUser(@PathVariable UUID id) {
        return this.oThreadService.findByUser(id);
    }
}
