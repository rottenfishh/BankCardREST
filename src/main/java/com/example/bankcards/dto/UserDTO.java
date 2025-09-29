package com.example.bankcards.dto;

import com.example.bankcards.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Шаблон данных о пользователе")
public class UserDTO {
    Long id;
    String userName;
    User.ROLE role;
}
