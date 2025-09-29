package com.example.bankcards.dto.cards;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardBlockRequestForAdmin {
    Long userId;
    Long cardId;
    com.example.bankcards.entity.CardBlockRequest.BlockStatus blockStatus;
}
