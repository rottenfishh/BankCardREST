package com.example.bankcards.dto;

import com.example.bankcards.entity.CardBlockRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Вид запроса на блокировку карты")
public class CardBlockRequestDTO {
    Long userId;
    Long cardId;
    CardBlockRequest.BlockStatus blockStatus;
}
