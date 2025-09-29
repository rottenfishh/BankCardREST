package com.example.bankcards.controller;

import com.example.bankcards.dto.cards.BlockRequestFromUser;
import com.example.bankcards.dto.cards.CardBlockRequestForAdmin;
import com.example.bankcards.repository.CardBlockRequestRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.security.CustomUserDetails;
import com.example.bankcards.service.CardBlockRequestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/block-requests")
@RequiredArgsConstructor
public class CardBlockRequestController {
    private final CardBlockRequestRepository cardBlockRequestRepository;
    private final CardBlockRequestService cardBlockRequestService;
    private final CardRepository cardRepository;

    @Operation(summary = "Получить все запросы на блокировку карт", tags = {"Админ"})
    @GetMapping("/admin")
    public List<BlockRequestFromUser> getCardBlockRequests() {
        return cardBlockRequestService.getBlockRequests();
    }

    @Operation(summary = "Аппрувнуть запрос на блокировку карты", tags = {"Админ"})
    @PutMapping("/admin/{idx}")
    public void approveBlockRequest(@PathVariable Long idx, @RequestBody CardBlockRequestForAdmin blockRequest) {
        cardBlockRequestService.approveCardBlock(blockRequest);
    }

    @Operation(summary = "Запросить блокировку карты", tags = {"Пользователь"})
    @PostMapping
    public void createCardBlockRequest(@AuthenticationPrincipal CustomUserDetails auth, @RequestBody BlockRequestFromUser blockRequestFromUser) {
        cardBlockRequestService.requestBlockCard(auth.getId(), blockRequestFromUser);
    }

}
