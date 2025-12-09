package ru.rutmiit.services;

import ru.rutmiit.models.entities.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    List<Chat> allChats();

    Optional<Chat> chatInfo(String name);
}
