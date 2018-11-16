package ua.challenge.service;

import ua.challenge.entity.User;

import java.util.List;

public interface UserService {
    void create();

    List<User> getUsers();
}
