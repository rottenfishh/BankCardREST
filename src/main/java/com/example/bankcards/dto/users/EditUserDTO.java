package com.example.bankcards.dto.users;

import com.example.bankcards.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Вид запроса от админа на редактирование данных пользователя")
@AllArgsConstructor
public class EditUserDTO {
    String name;
    User.ROLE role;
}
