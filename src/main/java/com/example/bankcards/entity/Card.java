package com.example.bankcards.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "cards")
@Schema(description = "Банковская карта пользователя")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор карты", example = "1")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Номер карты", example = "1234567812345678")
    private String cardNumber;

    @Column(nullable = false)
    @Schema(description = "Дата окончания действия карты", example = "2027-12-31")
    private Date expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Статус карты", example = "ACTIVE")
    private STATUS status;

    @Column(nullable = false)
    @Schema(description = "Баланс карты", example = "1000.0")
    private double balance;

    @JoinColumn(name = "UserId", nullable = false)
    @ManyToOne
    @Schema(description = "Владелец карты")
    private User user;

    public enum STATUS {
        ACTIVE,
        BLOCKED,
        EXPIRED;
    }
}


