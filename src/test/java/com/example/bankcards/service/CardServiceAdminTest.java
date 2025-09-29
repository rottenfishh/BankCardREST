package com.example.bankcards.service;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceAdminTest {
    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardService cardService;

    User user;
    User admin;
    Card card1;
    Card card2;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("Boba");
        user.setId(1L);
        user.setPassword("password");
        user.setRole(User.ROLE.USER);

        admin = new User();
        admin.setUsername("Admin");
        admin.setId(100L);
        admin.setPassword("secretPassword");
        admin.setRole(User.ROLE.ADMIN);

        card1 = new Card();
        card1.setStatus(Card.STATUS.ACTIVE);
        card1.setId(1L);
        card1.setUser(user);
        card1.setBalance(0.0);

        card2 = new Card();
        card2.setStatus(Card.STATUS.ACTIVE);
        card2.setId(2L);
        card2.setUser(user);
        card2.setBalance(5.0);
    }

    @Test
    public void testGetAllCards() {
        List<Card> repoCards = new ArrayList<>();
        repoCards.add(card1);
        repoCards.add(card2);
        assertEquals(2, repoCards.size());
        System.out.println("repoCards: " + repoCards);

        when(cardRepository.findAll()).thenReturn(repoCards);
        List<Card> userCards = cardService.getCardsListFiltered(null, null);
        System.out.println("userCards: " + userCards);
        assertEquals(2, userCards.size());
    }

    //rest are kind of useless to test
}
