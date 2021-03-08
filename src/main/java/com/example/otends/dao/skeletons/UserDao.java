package com.example.otends.dao.skeletons;

import com.example.otends.entities.User;

import java.util.Optional;

public interface UserDao extends EntityDao {
    Optional<User> login(String email, String password);
}
