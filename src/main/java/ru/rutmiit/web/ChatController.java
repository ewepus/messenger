package ru.rutmiit.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rutmiit.dto.CreateChatDto;
import ru.rutmiit.dto.CreateMessageDto;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.Message;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.enums.ChatStatus;
import ru.rutmiit.models.exceptions.ChatNotFoundException;
import ru.rutmiit.models.exceptions.RecursionException;
import ru.rutmiit.models.exceptions.UserNotFoundException;
import ru.rutmiit.services.AuthService;
import ru.rutmiit.services.ChatService;
import ru.rutmiit.services.MessageService;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    private final AuthService authService;

    private final MessageService messageService;

    public ChatController(ChatService chatService, AuthService authService, MessageService messageService) {
        this.chatService = chatService;
        this.authService = authService;
        this.messageService = messageService;
        log.info("ChatController инициализирован");
    }

    @GetMapping("/create/{name}")
    public String createChat(@PathVariable("name") String username, Model model, Principal principal) {
        String targetUsername = principal.getName();
        if (username.equals(targetUsername)) {
            throw new RecursionException("Невозможно создать чат с самим собой!");
        }

        log.debug("Отображение формы создания чата");
        model.addAttribute("username", username);
        return "chat-create";
    }

    @ModelAttribute("chatModel")
    public CreateChatDto initChat() {
        return new CreateChatDto();
    }

    @ModelAttribute("messageModel")
    public CreateMessageDto initMessage() {
        return new CreateMessageDto();
    }

    @PostMapping("/create/{name}")
    public String createChat(@Valid CreateChatDto chatModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal,
                             @PathVariable("name") String username) {
        log.debug("Обработка POST запроса на создание чата");

        if (bindingResult.hasErrors()) {
            log.warn("Ошибки валидации при создании чата: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("chatModel", chatModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.chatModel",
                    bindingResult);
            return "redirect:/chats/create";
        }

        String targetUsername = principal.getName();

        if (username.equals(targetUsername)) {
            throw new RecursionException("Невозможно создать чат с самим собой!");
        }

        User targetUser = authService.getUser(targetUsername);
        User user = authService.getUser(username);

        chatService.createChat(List.of(targetUser, user), chatModel);

        redirectAttributes.addFlashAttribute("successMessage",
                "Чат '" + chatModel.getName() + "' успешно создан!");

        return "redirect:/chats/";
    }

    @GetMapping("/")
    public String chats(Principal principal, Model model) {
        String username = principal.getName();
        log.debug("Отображение списка чатов пользователя: {}", username);

        User user = authService.getUser(username);

        List<Chat> chats = chatService.allChats().stream().filter(c -> c.getStatus().equals(ChatStatus.ACTIVE))
                .filter(chat -> chat.getUsers()
                .stream().map(User::getUsername).toList()
                .contains(user.getUsername())).toList();

        model.addAttribute("chats", chats);

        return "chats-all";
    }

    @GetMapping("/chat/{name}")
    public String showChat(@PathVariable("name") String name, Model model) {
        Optional<Chat> chatOptional = chatService.chatInfo(name);

        if (chatOptional.isEmpty()) {
            throw new ChatNotFoundException("Чата с таким названием не существует");
        }

        List<Message> messages = messageService.allMessages().stream()
                .filter(message -> message.getChat().getId().equals(chatOptional.get().getId()))
                .sorted(Comparator.comparing(Message::getSentAt).reversed())
                .collect(Collectors.toList());

        log.debug("Отображение чата: {}", name);
        model.addAttribute("messages", messages);
        model.addAttribute("chatDetails", chatOptional.get());

        return "chat-specific";
    }

    @PostMapping("/chat/{name}")
    public String showChat(@Valid CreateMessageDto messageModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal,
                           @PathVariable("name") String chatName) {
        log.debug("Обработка POST запроса на создание сообщения");

        if (bindingResult.hasErrors()) {
            log.warn("Ошибки валидации при создании сообщения: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("messageModel", messageModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.messageModel",
                    bindingResult);
            return "redirect:" + UriComponentsBuilder.fromPath("/chats/chat/{chatName}")
                    .buildAndExpand(chatName)
                    .encode()
                    .toUriString();
        }

        String username = principal.getName();
        User user = authService.getUser(username);
        Optional<Chat> chat = chatService.chatInfo(chatName);
        if (chat.isEmpty()) throw new ChatNotFoundException("Чата с таким названием не существует");

        messageService.create(messageModel, chat.get(), user);

        redirectAttributes.addFlashAttribute("successMessage",
                "Сообщение '" + messageModel.getText() + "' успешно создано!");

        return "redirect:" + UriComponentsBuilder.fromPath("/chats/chat/{chatName}")
                .buildAndExpand(chatName)
                .encode()
                .toUriString();
    }
}
