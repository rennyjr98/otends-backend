package com.example.otends.dao;

import com.example.otends.dao.skeletons.UserDao;
import com.example.otends.entities.User;
import com.example.otends.entities.models.OEntity;
import com.example.otends.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("UserDaoService")
public class UserDaoService implements UserDao {
    @Autowired
    private IUserRepository _repository;

    @Override
    public int add(OEntity entity) {
        User user = (User) entity;
        this._repository.save(user);
        return 1;
    }

    @Override
    public List<User> getAll() {
        return this._repository.findAll();
    }

    @Override
    public Optional<User> get(UUID id) {
        return this._repository.findById(id);
    }

    @Override
    public int delete(UUID id) {
        return 0;
    }

    @Override
    public int update(UUID id, OEntity user) {
        return 0;
    }

    @Override
    public Optional<User> login(String email, String password) {
        return this._repository.findByLogin(email, password);
    }
}
