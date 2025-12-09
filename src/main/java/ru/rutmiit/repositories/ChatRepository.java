package ru.rutmiit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rutmiit.models.entities.Chat;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    Optional<Chat> findChatByName(String name);
}
