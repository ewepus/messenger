package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.dto.CreateChatDto;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.repositories.ChatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ModelMapper mapper;

    public ChatServiceImpl(ChatRepository chatRepository, ModelMapper mapper) {
        this.chatRepository = chatRepository;
        this.mapper = mapper;
        log.info("ChatServiceImpl инициализирован");
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "chats", allEntries = true)
    public void createChat(CreateChatDto createChatDto) {
        log.debug("Создание нового чата: {}", createChatDto.getName());
        Chat chat = mapper.map(createChatDto, Chat.class);
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
