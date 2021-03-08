package com.example.otends.entities.models;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class OEntity {
    protected boolean active;

    public OEntity() {

    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
