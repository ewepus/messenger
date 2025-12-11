package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.repositories.UserRepository;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("UserServiceImpl инициализирован");
    }


    @Override
    public List<User> allUsers() {
        log.debug("Получение списка всех пользователей");
        List<User> users = new ArrayList<>(userRepository.findAll());
        log.debug("Найдено пользователей: {}", users.size());
        log.debug("Пользователи: {}", users);
        return users;
    }

    @Override
    public Optional<User> userInfo(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Object[]> top5Users() {
        return userRepository.findTopUsers()
                .stream()
                .limit(5)
                .toList();
    }
}