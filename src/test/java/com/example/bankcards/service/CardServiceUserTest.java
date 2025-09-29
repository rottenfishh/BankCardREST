package com.example.bankcards.service;

import com.example.bankcards.dto.cards.CardDTO;
import com.example.bankcards.dto.cards.TransferDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceUserTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardService cardService;

    User user;
    Card cardFrom;
    Card cardTo;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        cardFrom = new Card();
        cardFrom.setId(1L);
        cardFrom.setUser(user);
        cardFrom.setBalance(500.0);
        cardFrom.setStatus(Card.STATUS.ACTIVE);
        cardFrom.setCardNumber("2222222222222222");

        cardTo = new Card();
        cardTo.setId(2L);
        cardTo.setUser(user);
        cardTo.setBalance(0.0);
        cardTo.setStatus(Card.STATUS.ACTIVE);
        cardTo.setCardNumber("2222222222222223");
    }
    @Test
    void testGetBalance() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        double balance = cardService.getBalance(1L, 1L);
        assertEquals(500.0, balance);
    }

    @Test
    void testGetCards() {
        List<Card> repoCards = new ArrayList<>();
        repoCards.add(cardFrom);
        repoCards.add(cardTo);

        when(cardRepository.getCardsByUserId(1L)).thenReturn(repoCards);

        List<CardDTO> userCards = cardService.getCardsByUser(1L, null);

        assertEquals(2, userCards.size());
    }
    @Test
    void testTransferMoney() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(cardTo));

        TransferDTO transferDTO = new TransferDTO(cardFrom.getId(), cardTo.getId(), 500.0);
        cardService.transferMoney(user.getId(), transferDTO);
        double balance = cardService.getBalance(1L, 1L);
        assertEquals(0.0, balance);
        double balanceTo = cardService.getBalance(1L, 2L);
        assertEquals(500.0, balanceTo);
    }

    @Test
    void testTransferMoneyNotEnoughBalance() {
        cardFrom.setBalance(400.0); //less money than needed

        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(cardTo));
        TransferDTO transferDTO = new TransferDTO(cardFrom.getId(), cardTo.getId(), 500.0);
        assertThrows(IllegalStateException.class, () -> cardService.transferMoney(user.getId(), transferDTO));
    }

    @Test
    void testTransferMoneyCardBlockedState() {
        cardTo.setStatus(Card.STATUS.BLOCKED);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(cardTo));
        TransferDTO transferDTO = new TransferDTO(cardFrom.getId(), cardTo.getId(), 500.0);
        assertThrows(IllegalStateException.class, () -> cardService.transferMoney(user.getId(), transferDTO));
    }

    @Test
    void testTransferMoneyAccessDenied() {
        User userNew = new User();
        userNew.setId(3L);
        cardTo.setUser(userNew);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(cardTo));

        TransferDTO transferDTO = new TransferDTO(cardFrom.getId(), cardTo.getId(), 500.0);
        assertThrows(AccessDeniedException.class, () -> cardService.transferMoney(user.getId(), transferDTO));
    }

}

