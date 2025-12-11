package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.dto.CreateMessageDto;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.Message;
import ru.rutmiit.models.entities.User;
import ru.rutmiit.models.enums.ChatStatus;
import ru.rutmiit.repositories.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        log.info("MessageServiceImpl инициализирован");
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "messages", allEntries = true)
    public void create(CreateMessageDto createMessageDto, Chat chat, User user) {
        log.debug("Создание нового сообщения: {}", createMessageDto);

        Message message = new Message(createMessageDto.getText(), LocalDateTime.now(), chat, user);
        messageRepository.save(message);
        log.info("Сообщение успешно создано: {}, {}",
                message.getId(), message.getSentAt());
    }

    @Override
    public List<Message> allMessages() {
        log.debug("Получение списка всех сообщений");
        List<Message> messages = messageRepository.findAll();
        log.debug("Найдено сообщений: {}", messages.size());
        log.debug("Сообщения: {}", messages);
        return messages;
    }

    @Override
    public Optional<Message> messageInfo(String id) {
        return messageRepository.findById(id);
    }
}
