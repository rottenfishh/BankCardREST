package com.example.bankcards.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "card_block_request")
@Schema(description = "Запрос на блокировку карты от пользователя")
public class CardBlockRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор запроса", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Уникальный идентификатор запрашиваемой на блокировку карты", example = "1")
    private Long cardId;

    @Column(nullable = false)
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Текущий статус запроса", example = "REQUESTED")
    private BlockStatus status;

    public enum BlockStatus {
        REQUESTED,
        APPROVED,
        REJECTED,
    }
}
