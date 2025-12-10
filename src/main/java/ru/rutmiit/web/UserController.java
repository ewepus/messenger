package ru.rutmiit.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.exceptions.UserNotFoundException;
import ru.rutmiit.services.UserService;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String showAllUsers(Model model) {
        log.debug("Отображение списка всех пользователей");
        model.addAttribute("allUsers", userService.allUsers());

        return "user-all";
    }

    @GetMapping("/user-details/{username}")
    public String showUserDetails(@PathVariable("username") String username, Model model, Principal principal) {
        Optional<User> userOptional = userService.userInfo(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователя с таким username не существует");
        }

        String targetUsername = principal.getName();
        if (username.equals(targetUsername)) {
            log.debug("Отображение деталей пользователя: {}", username);
            model.addAttribute("userDetails", userOptional.get());
            return "user-details-self";
        }

        log.debug("Отображение деталей пользователя: {}", username);
        model.addAttribute("userDetails", userOptional.get());
        return "user-details";
    }
}
