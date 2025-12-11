package ru.rutmiit.services;

import ru.rutmiit.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> allUsers();

    Optional<User> userInfo(String username);

    List<Object[]> top5Users();
}