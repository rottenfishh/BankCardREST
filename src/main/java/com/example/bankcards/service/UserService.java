package com.example.bankcards.service;

import com.example.bankcards.dto.users.CreateUserDTO;
import com.example.bankcards.dto.users.EditUserDTO;
import com.example.bankcards.dto.users.UserDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserExistsException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public User createUser(CreateUserDTO createUserDTO) {
        if (userRepository.getUsersByUsername(createUserDTO.getName()) != null) {
            throw new UserExistsException("Пользователь с таким именем уже существует");
        }
        User user = new User();
        user.setUsername(createUserDTO.getName());
        user.setPassword(createUserDTO.getPassword());
        user.setRole(createUserDTO.getRole());
        userRepository.save(user);
        return user;
    }

    public void editUser(Long id, EditUserDTO editUserDTO) {
        if (editUserDTO.getName() != null) {
            User user = userRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Пользователя с " + id + "не существует"));
            user.setUsername(editUserDTO.getName());
            userRepository.save(user);
        }
        if (editUserDTO.getRole() != null) {
            User user = userRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Пользователя с " + id + "не существует"));
            user.setRole(editUserDTO.getRole());
            userRepository.save(user);
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Пользователя с " + id + "не существует"));
    }

    public UserDTO convertDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
