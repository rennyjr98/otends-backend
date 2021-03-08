package com.example.otends.entities;

import com.example.otends.entities.models.OEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class RawDataCfg extends OEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "raw_data_id", length = 16)
    public UUID id;
    public boolean isBinary;
    public boolean isInvert;
    public boolean isPublic;
    public boolean isClassificator;
    public int inputRows;
    public int outputRows;
    @ElementCollection
    public List<String> inputLabels;
    @ElementCollection
    public List<String> outputLabels;
    public String apiURL;
    public RawDataCfg() {
        super();
        init();
    }

    public RawDataCfg(UUID uuid) {
        this.id = uuid;
        init();
    }

    public RawDataCfg(boolean... settings) {
        super();
        this.isBinary = settings[0];
        this.isInvert = settings[1];
        this.isClassificator = settings[2];
    }

    private void init() {
        this.isBinary =
        this.isInvert = false;
        this.isClassificator = false;
    }
}
