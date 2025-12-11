package ru.rutmiit.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import ru.rutmiit.models.enums.ChatStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chats")
public class Chat extends BaseEntity implements Serializable {

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatStatus status;

    @CreatedDate
    @Getter
    @Setter
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chat_affiliations",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Getter
    @Setter
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Message> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(String name, ChatStatus status, LocalDateTime createdAt) {
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Chat(String name, ChatStatus status, LocalDateTime createdAt, List<User> users) {
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.users = users;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}
