package ru.rutmiit.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import ru.rutmiit.models.enums.UserRoles;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @CreatedDate
    @Getter
    @Setter
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private UserRoles name;

    public Role(UserRoles name) {
        this.name = name;
    }

    public Role(LocalDateTime createdAt, UserRoles name) {
        this.createdAt = createdAt;
        this.name = name;
    }

    public Role() {

    }

    public UserRoles getName() {
        return name;
    }

    public void setName(UserRoles name) {
        this.name = name;
    }
}