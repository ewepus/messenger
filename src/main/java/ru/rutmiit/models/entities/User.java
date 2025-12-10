package ru.rutmiit.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Getter
    @Setter
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chat_affiliations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    @Getter
    @Setter
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Message> messages = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
