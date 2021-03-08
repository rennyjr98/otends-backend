package com.example.otends.entities;

import com.example.otends.entities.models.OEntity;
import com.example.otends.entities.skeletons.IOThread;
import com.example.otends.utils.NeuralNetwork;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Entity
public class OThread extends OEntity implements IOThread {
    @Transient public static String BRAIN_BASEURL = "src/main/java/com/example/otends/brains/";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "othread_id", length = 16)
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @Transient
    @JsonIgnore
    private NeuralNetwork nn;
    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "raw_data_id")
    private RawDataCfg cfg;
    public OThread() {
        super();
        this.cfg = new RawDataCfg();
    }

    public OThread(int inputs, int hiddens, int outputs) {
        super();
        this.nn = new NeuralNetwork(inputs, hiddens, outputs);
        this.cfg = new RawDataCfg();
    }

    public void load() {
        File data = new File(OThread.BRAIN_BASEURL + this.id + ".mind");
        if(data.exists()) {
            try {
                this.nn = NeuralNetwork.deserialize(data.getAbsolutePath());
            } catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public NeuralNetwork getNn() {
        return nn;
    }
    public User getOwner() {
        return this.owner;
    }
    public RawDataCfg getCfg() {
        return this.cfg;
    }

    public void createNN(int inputs, int hiddens, int outputs) {
        this.nn = new NeuralNetwork(inputs, hiddens, outputs);
    }

    @Override
    public IOThread Builder() {
        return null;
    }

    public void train(double[] data, double[] targets) {
        if(this.nn != null) {
            this.nn.train(data, targets);
        }
    }

    public void save() {
        try {
            this.nn.serialize(new File(OThread.BRAIN_BASEURL + this.id + ".mind")
                    .getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean state() {
        boolean nnInit = this.nn != null;
        return nnInit;
    }
}
