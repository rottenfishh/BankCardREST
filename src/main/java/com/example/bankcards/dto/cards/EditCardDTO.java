package com.example.bankcards.dto.cards;

import com.example.bankcards.entity.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Вид запроса от админа на редактирование данных о карте")
public class EditCardDTO {
    Double balance;
    Card.STATUS status;
    Date expirationDate;
}
