package ru.rutmiit.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rutmiit.models.entities.Message;
import ru.rutmiit.repositories.MessageRepository;

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
    public List<Message> allMessages() {
        log.debug("Получение списка всех сообщений");
        List<Message> messages = messageRepository.findAll();
        log.debug("Найдено сообщений: {}", messages.size());
        log.debug("Сообщения: {}", messages);
        return messages;
    }

    @Override
    public Optional<Message> messageInfo(String senderId) {
        return messageRepository.findMessageBySenderId(senderId);
    }
}
