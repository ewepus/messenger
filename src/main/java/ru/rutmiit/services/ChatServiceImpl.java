package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.repositories.ChatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        log.info("ChatServiceImpl инициализирован");
    }

    @Override
    public List<Chat> allChats() {
        log.debug("Получение списка всех чатов");
        List<Chat> chats = new ArrayList<>(chatRepository.findAll());
        log.debug("Найдено чатов: {}", chats.size());
        log.debug("Чаты: {}", chats);
        return chats;
    }

    @Override
    public Optional<Chat> chatInfo(String name) {
        return chatRepository.findChatByName(name);
    }
}
