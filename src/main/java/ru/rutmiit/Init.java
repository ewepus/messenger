package ru.rutmiit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.Role;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.enums.ChatStatus;
import ru.rutmiit.models.enums.UserRoles;
import ru.rutmiit.repositories.ChatRepository;
import ru.rutmiit.repositories.UserRepository;
import ru.rutmiit.repositories.UserRoleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Инициализация начальных данных при запуске приложения.
 */
@Slf4j
@Component
public class Init implements CommandLineRunner {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public Init(ChatRepository chatRepository, UserRepository userRepository,
                UserRoleRepository userRoleRepository,
                PasswordEncoder passwordEncoder,
                @Value("${app.default.password}") String defaultPassword) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
        log.info("Init компонент инициализирован");
    }

    @Override
    public void run(String... args) {
        log.info("Запуск инициализации начальных данных");
        initRoles();
        initUsers();
        initChats();
        log.info("Инициализация начальных данных завершена");
    }

    private void initChats() {
        if (chatRepository.count() == 0) {
            log.info("Создание базовых чатов...");
            Optional<User> user = userRepository.findByUsername("user");
            List<User> userList = new ArrayList<>();
            user.ifPresent(userList::add);

            chatRepository.saveAll(List.of(
                    new Chat("Чат №1", ChatStatus.ACTIVE, LocalDateTime.now(), userList),
                    new Chat("Чат №2", ChatStatus.ACTIVE, LocalDateTime.now(), userList),
                    new Chat("Чат №3", ChatStatus.ACTIVE, LocalDateTime.now(), userList)
            ));
            log.info("Чаты созданы");
        } else {
            log.debug("Чаты уже существуют, пропуск инициализации");
        }
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            log.info("Создание базовых ролей...");
            userRoleRepository.saveAll(List.of(
                    new Role(LocalDateTime.now(), UserRoles.ADMIN),
                    new Role(LocalDateTime.now(), UserRoles.MODERATOR),
                    new Role(LocalDateTime.now(), UserRoles.USER)
            ));
            log.info("Роли созданы: ADMIN, MODERATOR, USER");
        } else {
            log.debug("Роли уже существуют, пропуск инициализации");
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            log.info("Создание пользователей по умолчанию...");
            initAdmin();
            initModerator();
            initNormalUser();
            log.info("Пользователи по умолчанию созданы");
        } else {
            log.debug("Пользователи уже существуют, пропуск инициализации");
        }
    }

    private void initAdmin() {
        var adminRole = userRoleRepository
                .findRoleByName(UserRoles.ADMIN)
                .orElseThrow();

        var adminUser = new User(
                "admin",
                passwordEncoder.encode(defaultPassword),
                "8 800 555 35 35",
                "admin@example.com"
        );
        adminUser.setRoles(List.of(adminRole));
        userRepository.save(adminUser);
        log.info("Создан администратор: admin");
    }

    private void initModerator() {
        var moderatorRole = userRoleRepository
                .findRoleByName(UserRoles.MODERATOR)
                .orElseThrow();

        var moderatorUser = new User(
                "moderator",
                passwordEncoder.encode(defaultPassword),
                "8 215 124 12 54",
                "moderator@example.com"
        );
        moderatorUser.setRoles(List.of(moderatorRole));
        userRepository.save(moderatorUser);
        log.info("Создан модератор: moderator");
    }

    private void initNormalUser() {
        var userRole = userRoleRepository
                .findRoleByName(UserRoles.USER)
                .orElseThrow();

        var normalUser = new User(
                "user",
                passwordEncoder.encode(defaultPassword),
                "+7 145 564 14 64",
                "user@example.com"
        );
        normalUser.setRoles(List.of(userRole));
        userRepository.save(normalUser);
        log.info("Создан обычный пользователь: user");
    }
}
