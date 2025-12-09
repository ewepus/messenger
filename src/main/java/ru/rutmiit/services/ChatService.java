package ru.rutmiit.services;

import ru.rutmiit.dto.CreateChatDto;
import ru.rutmiit.models.entities.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    void createChat(CreateChatDto createChatDto);

    List<Chat> allChats();

    Optional<Chat> chatInfo(String name);
}
