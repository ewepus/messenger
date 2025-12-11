package ru.rutmiit.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity implements Serializable {

    @Getter
    @Setter
    @Column(nullable = false, precision = 4096)
    private String text;

    @CreatedDate
    @Getter
    @Setter
    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    @Getter
    @Setter
    private Chat chat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    @Getter
    @Setter
    private User user;

    public Message() {
    }

    public Message(String text, LocalDateTime sentAt, Chat chat, User user) {
        this.text = text;
        this.sentAt = sentAt;
        this.chat = chat;
        this.user = user;
    }
}
