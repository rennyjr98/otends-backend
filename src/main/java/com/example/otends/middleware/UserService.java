package com.example.otends.middleware;

import com.example.otends.dao.skeletons.UserDao;
import com.example.otends.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserDao userDao;
    @Autowired
    public UserService(@Qualifier("UserDaoService")UserDao userDao) {
        this.userDao = userDao;
    }

    public int add(User user) {
        return userDao.add(user);
    }

    public List<User> getAll() {
        return this.userDao.getAll();
    }

    public Optional<User> get(UUID id) {
        return this.userDao.get(id);
    }

    public Optional<User> login(String email, String password) {
        return this.userDao.login(email, password);
    }
}
