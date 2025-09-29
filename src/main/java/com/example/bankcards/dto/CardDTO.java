package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "Информация о карте (для отображения пользователю)")
public class CardDTO {
    private Long ownerId;
    private Long id;
    private String maskedCardNumber;
    private Date expiratonDate;
    private Card.STATUS status;
    private double balance;
}
