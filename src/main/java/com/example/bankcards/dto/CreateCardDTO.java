package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "Вид запроса от админа на создание карты")
public class CreateCardDTO {
    private Long ownerId;
    private String cardNumber;
    private Date expirationDate;
}
