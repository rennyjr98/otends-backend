package com.example.otends.controller;

import com.example.otends.entities.OThread;
import com.example.otends.entities.jsonserializable.FarmModel;
import com.example.otends.entities.jsonserializable.FarmerModel;
import com.example.otends.entities.jsonserializable.IncubatorModel;
import com.example.otends.entities.jsonserializable.OThreadModel;
import com.example.otends.middleware.api.FarmService;
import com.example.otends.utils.Farm;
import com.example.otends.utils.Farmer;
import com.example.otends.utils.Incubator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/farm")
@RestController
public class FarmController {
    private final FarmService _farmService;
    private final ModelMapper _mapper;
    @Autowired
    public FarmController(FarmService farmService) {
        this._farmService = farmService;
        this._mapper = new ModelMapper();
        this._mapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        _farmService.start();
    }

    @GetMapping
    public List<FarmModel> getAllFarms() {
        List<FarmModel> farms = this._farmService.getFarms()
                .stream()
                .map(farm -> _mapper.map(farm, FarmModel.class))
                .collect(Collectors.toList());

        return farms;
    }
}
