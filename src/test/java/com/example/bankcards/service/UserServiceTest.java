package com.example.bankcards.service;

import com.example.bankcards.dto.CreateUserDTO;
import com.example.bankcards.dto.EditUserDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserExistsException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private UserService userService;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("Boba");
        user.setPassword("pass");
        user.setRole(User.ROLE.USER);
    }

    @Test
    void testCreateUserSuccess() {
        CreateUserDTO dto = new CreateUserDTO("Boba", "password", User.ROLE.USER);

        // мокаем, что пользователя с таким именем нет
        when(userRepository.getUsersByUsername("Boba")).thenReturn(null);

        User created = userService.createUser(dto);

        assertEquals("Boba", created.getUsername());
        assertEquals("password", created.getPassword());
        verify(userRepository).save(any(User.class)); // проверяем что save вызван
    }

    @Test
    void testCreateUserAlreadyExists() {
        CreateUserDTO dto = new CreateUserDTO("Boba", "password", User.ROLE.USER);

        when(userRepository.getUsersByUsername("Boba")).thenReturn(new User());

        assertThrows(UserExistsException.class, () -> userService.createUser(dto));
        verify(userRepository, never()).save(any()); // save не вызывается
    }

    @Test
    void testEditUserName() {
        EditUserDTO dto = new EditUserDTO("newBoba", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.editUser(1L, dto);

        assertEquals("newBoba", user.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void testEditUserRole() {
        EditUserDTO dto = new EditUserDTO(null, User.ROLE.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.editUser(1L, dto);

        assertEquals(User.ROLE.ADMIN, user.getRole());
        verify(userRepository).save(user);
    }

    @Test
    void testEditUserNotFound() {
        EditUserDTO dto = new EditUserDTO("newName", null);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.editUser(1L, dto));
    }

    @Test
    void testGetUsers() {
        List<User> list = new ArrayList<>();
        list.add(user);

        when(userRepository.findAll()).thenReturn(list);

        List<User> result = userService.getUsers();
        assertEquals(1, result.size());
        assertEquals("Boba", result.get(0).getUsername());
    }

    @Test
    void testGetUserByIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertEquals("Boba", result.getUsername());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
