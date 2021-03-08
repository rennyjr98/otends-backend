package com.example.otends.entities.jsonserializable;

import com.example.otends.entities.RawDataCfg;

import java.util.UUID;

public class OThreadModel {
    public UUID id;
    public String name;
    public UserModel owner;
    public RawDataCfg cfg;
    public boolean status;
}
