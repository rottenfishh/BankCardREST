package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Шаблон запроса на перевод денег с одной карты пользователя на другую")
public class TransferDTO {
    Long fromCardId;
    Long toCardId;
    double amount;
}
