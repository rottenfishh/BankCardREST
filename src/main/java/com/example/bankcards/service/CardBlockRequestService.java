package com.example.bankcards.service;

import com.example.bankcards.dto.CardBlockRequestDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardBlockRequest;
import com.example.bankcards.repository.CardBlockRequestRepository;
import com.example.bankcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardBlockRequestService {
    private final CardBlockRequestRepository cardBlockRequestRepository;
    private final CardRepository cardRepository;

    public CardBlockRequestDTO convertDTO(CardBlockRequest cardBlockRequest) {
        return new CardBlockRequestDTO(cardBlockRequest.getUserId(),
                cardBlockRequest.getId(), cardBlockRequest.getStatus());
    }
    public void requestBlockCard(CardBlockRequestDTO requestDTO) {
        Card card = cardRepository.findById(requestDTO.getCardId()).orElse(null);
        if (card == null || !Objects.equals(card.getUser().getId(), requestDTO.getUserId())) {
            System.err.println("Card does not belong to user");
            return;
        } else {
            CardBlockRequest cardBlockRequest = new CardBlockRequest();
            cardBlockRequest.setCardId(card.getId());
            cardBlockRequest.setUserId(requestDTO.getUserId());
            cardBlockRequestRepository.save(cardBlockRequest);
            // add card for block or unblock request list to admin
        }
    }

    public void approveCardBlock(CardBlockRequestDTO requestDTO) {
        Card card = cardRepository.findById(requestDTO.getCardId()).orElse(null);
        if (card == null || !Objects.equals(card.getUser().getId(), requestDTO.getUserId())) {
            System.err.println("Card does not belong to user");
            return;
        } else {
            if (requestDTO.getBlockStatus() == CardBlockRequest.BlockStatus.APPROVED) {
                card.setStatus(Card.STATUS.BLOCKED);
            }
            cardRepository.save(card);
        }
    }

    public List<CardBlockRequestDTO> getBlockRequests() {
        return cardBlockRequestRepository.findAll().stream()
                .map(this::convertDTO).collect(Collectors.toList());
    }
}
