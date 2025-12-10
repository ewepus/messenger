package ru.rutmiit.services;

import ru.rutmiit.models.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> allMessages();

    Optional<Message> messageInfo(String senderId);
}
