package ru.rutmiit.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rutmiit.dto.CreateChatDto;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.exceptions.ChatNotFoundException;
import ru.rutmiit.models.exceptions.UserNotFoundException;
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

    @GetMapping("/create")
    public String createChat() {
        log.debug("Отображение формы создания чата");
        return "chat-create";
    }

    @ModelAttribute("chatModel")
    public CreateChatDto initChat() {
        return new CreateChatDto();
    }

    @PostMapping("/create")
    public String createChat(@Valid CreateChatDto chatModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        log.debug("Обработка POST запроса на создание чата");

        if (bindingResult.hasErrors()) {
            log.warn("Ошибки валидации при создании чата: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("chatModel", chatModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.chatModel",
                    bindingResult);
            return "redirect:/chats/create";
        }

        chatService.createChat(chatModel);
        redirectAttributes.addFlashAttribute("successMessage",
                "Чат '" + chatModel.getName() + "' успешно создан!");

        return "redirect:/chats/";
    }

    @GetMapping("/")
    public String chats(Principal principal, Model model) {
        String username = principal.getName();
        log.debug("Отображение списка чатов пользователя: {}", username);

        User user = authService.getUser(username);

        List<Chat> chats = chatService.allChats().stream().filter(chat -> chat.getUsers()
                .stream().map(User::getUsername).toList()
                .contains(user.getUsername())).toList();

        model.addAttribute("chats", chats);

        return "chats-all";
    }

    @GetMapping("/chat-details/{name}")
    public String showChatDetails(@PathVariable("name") String name, Model model) {
        Optional<Chat> chatOptional = chatService.chatInfo(name);

        if (chatOptional.isEmpty()) {
            throw new ChatNotFoundException("Чата с таким name не существует");
        }

        log.debug("Отображение деталей чата: {}", name);
        model.addAttribute("chatDetails", chatOptional.get());
        return "chat-details";
    }
}
