package com.example.bankcards.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Schema(description = "Пользователь")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    @Schema(description = "Имя пользователя", example = "Dio")
    String username;

    @Column(nullable = false)
    @Schema(description = "Зашифрованный пароль", example = "bebe")
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Роль пользователя", example = "USER")
    ROLE role;

    public enum ROLE {
        ADMIN,
        USER;
    }
}
