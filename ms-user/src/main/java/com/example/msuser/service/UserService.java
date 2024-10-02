package com.example.msuser.service;

import com.example.msuser.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> list();

    public Optional<User> getById(Integer id);

    public User save(User category);

    public User update(Integer id, User category);

    public void delete(Integer id);
}
