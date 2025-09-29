package com.example.bankcards.controller;

import com.example.bankcards.dto.cards.CardDTO;
import com.example.bankcards.dto.cards.CreateCardDTO;
import com.example.bankcards.dto.cards.EditCardDTO;
import com.example.bankcards.dto.cards.TransferDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.security.CustomUserDetails;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @Operation(summary = "Получить все карты", tags = {"Админ"})
    @GetMapping("/admin/cards")
    public List<Card> getCards(@AuthenticationPrincipal CustomUserDetails auth, @RequestParam(required = false) Long userId, @RequestParam(required = false) Card.STATUS status) {
        return cardService.getCardsListFiltered(userId, status); //change to query params
    }

    @Operation(summary = "Создать новую карту", tags = {"Админ"})
    @PostMapping("/admin/cards")
    public void createCard(@AuthenticationPrincipal CustomUserDetails auth, @RequestBody CreateCardDTO createCardDTO) {
        cardService.createCard(createCardDTO);
    }

    @Operation(summary = "Удалить карту", tags = {"Админ"})
    @DeleteMapping("/admin/cards/{idx}")
    public void deleteCard(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long idx) {
        cardService.deleteCard(idx);
    }

    @Operation(summary = "Обновить параметры карты", tags = {"Админ"})
    @PutMapping("/admin/cards/{idx}")
    public void updateCard(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long idx, @RequestBody EditCardDTO editCardDTO) {
        cardService.updateCard(idx, editCardDTO);
    }

    @Operation(summary = "Посмотреть свои карты", tags = {"Пользователь"})
    @GetMapping("/cards")
    public List<CardDTO> getCardsUser(@AuthenticationPrincipal CustomUserDetails auth, @RequestParam(name ="status", required = false) Card.STATUS status) {
        return cardService.getCardsByUser(auth.getId(), status);
    }

    @Operation(summary = "Посмотреть баланс конкретной карты", tags = {"Пользователь"})
    @GetMapping("/cards/{idx}/balance")
    public double getBalanceUser(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long idx) {
        return cardService.getBalance(auth.getId(), idx);
    }

    @Operation(summary = "Перевести денюжки", tags = {"Пользователь"})
    @PostMapping("/cards/transfer")
    public void transferMoney(@AuthenticationPrincipal CustomUserDetails auth, @RequestBody TransferDTO transferDTO) {
        cardService.transferMoney(auth.getId(), transferDTO);
    }

    @GetMapping("/cards/pages")
    public Page<CardDTO> getUserCards(
            @AuthenticationPrincipal CustomUserDetails auth,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return cardService.getUserCardsPages(auth.getId(), search, pageable);
    }
}
