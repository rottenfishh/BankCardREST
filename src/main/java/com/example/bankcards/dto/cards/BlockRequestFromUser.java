package com.example.bankcards.dto.cards;

import com.example.bankcards.entity.CardBlockRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Вид запроса на блокировку карты")
public class BlockRequestFromUser {
    Long cardId;
    CardBlockRequest.BlockStatus blockStatus;
}
