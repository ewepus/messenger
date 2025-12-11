package ru.rutmiit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rutmiit.models.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("""
            SELECT u.username, COUNT(m.id) as messageCount
            FROM User u
            JOIN u.messages m
            GROUP BY u.id, u.username
            ORDER BY messageCount DESC
            """)
    List<Object[]> findTopUsers();
}