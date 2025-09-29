package com.example.bankcards.dto.users;

import com.example.bankcards.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Вид запроса от админа на создание пользователя")
public class CreateUserDTO {
    String name;
    String password;
    User.ROLE role;
}
