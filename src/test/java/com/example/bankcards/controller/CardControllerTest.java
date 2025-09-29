package com.example.bankcards.controller;

import com.example.bankcards.entity.User;
import com.example.bankcards.exception.GlobalExceptionHandler;
import com.example.bankcards.security.CustomUserDetails;
import com.example.bankcards.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // Для поддержки Mockito
class CardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CardService cardService; // мок сервиса

    @InjectMocks
    private CardController cardController; // контроллер, в который инжектится мок

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController)
                .setControllerAdvice(new GlobalExceptionHandler()) // если есть @ControllerAdvice
                .build();
        // Создаём мок CustomUserDetails
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(User.ROLE.USER);
        CustomUserDetails userDetails = new CustomUserDetails(user); // mockUser() — ваш метод создания User
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

//    @Test
//    void getBalanceReturnsCorrectValue() throws Exception {
//        Long userId = 1L;
//        Long cardId = 1L;
//        double balance = 500.0;
//
//        when(cardService.getBalance(userId, cardId)).thenReturn(balance);
//
//        mockMvc.perform(get("/api/cards/{cardId}/balance", userId, cardId))
//                .andExpect(status().isOk())
//                .andExpect(content().string("500.0"));
//    }
//
//    @Test
//    void getBalanceThrowsWhenCardNotBelongsToUser() throws Exception {
//        Long userId = 1L;
//        Long cardId = 2L;
//
//        when(cardService.getBalance(userId, cardId))
//                .thenThrow(new IllegalStateException("Card does not belong to user"));
//
//        mockMvc.perform(get("/api/cards/{cardId}/balance", userId, cardId))
//                .andExpect(status().isInternalServerError());
//    }
}
