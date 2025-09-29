package com.example.bankcards.controller;

import com.example.bankcards.dto.users.CreateUserDTO;
import com.example.bankcards.dto.users.EditUserDTO;
import com.example.bankcards.dto.users.UserDTO;
import com.example.bankcards.security.CustomUserDetails;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// create delete user
// change their role idk admin endpoints
// get users(all or one)
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Tag(name = "Управления пользователями(админ)")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получить список пользователей")
    @GetMapping("/admin/users")
    public List<UserDTO> getAllUsers() {

        return userService.getUsers().stream()
                .map(userService::convertDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Получить пользователя по ид")
    @GetMapping("/admin/users/{idx}")
    public UserDTO getUser(@PathVariable Long idx) {
        return userService.convertDTO(userService.getUserById(idx));
    }

    @Operation(summary = "Изменить данные о пользователе")
    @PutMapping("/admin/users/{idx}")
    public UserDTO changeUser(@PathVariable Long idx, @RequestBody EditUserDTO editUserDTO) {
        userService.editUser(idx, editUserDTO);
        return userService.convertDTO(userService.getUserById(idx));
    }

    @Operation(summary = "Создать нового пользователя")
    @PostMapping("/admin/users")
    public UserDTO createUser(@AuthenticationPrincipal CustomUserDetails auth, @RequestBody CreateUserDTO userDTO) {
        return userService.convertDTO((userService.createUser(userDTO))); // so we can see result
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/admin/users/{idx}")
    public void deleteUser(@PathVariable Long idx) {
        userService.deleteUser(idx);
    }
}
