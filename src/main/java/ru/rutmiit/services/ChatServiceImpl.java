package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.dto.CreateChatDto;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.enums.ChatStatus;
import ru.rutmiit.repositories.ChatRepository;

import java.time.LocalDateTime;
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
    @Transactional
    @CacheEvict(cacheNames = "chats", allEntries = true)
    public void createChat(List<User> users, CreateChatDto createChatDto) {
        log.debug("Создание нового чата: {}", createChatDto.getName());

        Chat chat = new Chat(createChatDto.getName(), ChatStatus.ACTIVE, LocalDateTime.now(), users);
        chatRepository.save(chat);
        log.info("Чат успешно создан: {}, {}, {}", chat.getId(), chat.getName(), chat.getCreatedAt());
    }

    @Override
    public List<Chat> allChats() {
        log.debug("Получение списка всех чатов");
        List<Chat> chats = chatRepository.findAll();
        log.debug("Найдено чатов: {}", chats.size());
        log.debug("Чаты: {}", chats);
        return chats;
    }

    @Override
    public Optional<Chat> chatInfo(String name) {
        return chatRepository.findChatByName(name);
    }
}
