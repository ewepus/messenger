package ru.rutmiit.services;

import ru.rutmiit.dto.CreateMessageDto;
import ru.rutmiit.models.entities.Chat;
import ru.rutmiit.models.entities.Message;
import ru.rutmiit.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    void create(CreateMessageDto createMessageDto, Chat chat, User user);

    List<Message> allMessages();

    Optional<Message> messageInfo(String id);
}
