package ru.rutmiit.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.services.AuthService;
import ru.rutmiit.services.ChatService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    private final AuthService authService;

    public ChatController(ChatService chatService, AuthService authService) {
        this.chatService = chatService;
        this.authService = authService;
        log.info("ChatController инициализирован");
    }

    @GetMapping("/")
    public String chats(Principal principal, Model model) {
        String username = principal.getName();
        log.debug("Отображение списка чатов пользователя: {}", username);

        User user = authService.getUser(username);

        List<Chat> chats = chatService.allChats().stream().filter(chat -> chat.getUsers().contains(user)).toList();
//        List<Chat> chats = chatService.allChats().stream().

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(user);
        if (chats.isEmpty()) {
            System.out.println("Пусто");
        }
        for (Chat chat : chats) {
            System.out.println(chat);
        }
        model.addAttribute("chats", chats);

        return "chats-all";
    }
}
