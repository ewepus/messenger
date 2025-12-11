package ru.rutmiit.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.rutmiit.services.UserService;

@Slf4j
@Controller
public class HomepageController {
    private final UserService userService;

    public HomepageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        log.debug("Отображение главной страницы");
        model.addAttribute("topUsers", userService.top5Users());
        return "index";
    }
}
