package ru.rutmiit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rutmiit.models.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
}
