package com.example.otends.entities;

import com.example.otends.entities.models.OEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class User extends OEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", length = 16)
    private UUID id;
    private int role;
    private String name;
    private String email;
    private String nameCompany;
    private int typeCompany;
    private String password;

    public User() {
        super();
    }
    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }
    public User(UUID uuid) {
        super();
        this.id = uuid;
    }

    public UUID getId() {
        return id;
    }
    public int getRole() {
        return role;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getNameCompany() {
        return this.nameCompany;
    }
    public int getTypeCompany() {
        return this.typeCompany;
    }
    public String getPassword() {
        return password;
    }

    public void setRole(int role) {
        this.role = role;
    }
    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }
    public void setTypeCompany(int typeCompany) {
        this.typeCompany = typeCompany;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
