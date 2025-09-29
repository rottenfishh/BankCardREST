package com.example.bankcards.service;

import com.example.bankcards.dto.cards.BlockRequestFromUser;
import com.example.bankcards.dto.cards.CardBlockRequestForAdmin;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardBlockRequest;
import com.example.bankcards.repository.CardBlockRequestRepository;
import com.example.bankcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardBlockRequestService {
    private final CardBlockRequestRepository cardBlockRequestRepository;
    private final CardRepository cardRepository;

    public BlockRequestFromUser convertDTO(CardBlockRequest cardBlockRequest) {
        return new BlockRequestFromUser(cardBlockRequest.getUserId(),
                cardBlockRequest.getStatus());
    }
    public void requestBlockCard(Long userId, BlockRequestFromUser requestDTO) {
        Card card = cardRepository.findById(requestDTO.getCardId()).orElse(null);
        if (card == null || !Objects.equals(card.getUser().getId(), userId)) {
            throw new AccessDeniedException("Card does not belong to user");
        } else {
            CardBlockRequest cardBlockRequest = new CardBlockRequest();
            cardBlockRequest.setCardId(card.getId());
            cardBlockRequest.setUserId(userId);
            cardBlockRequestRepository.save(cardBlockRequest);
        }
    }

    public void approveCardBlock(CardBlockRequestForAdmin requestDTO) {
        Card card = cardRepository.findById(requestDTO.getCardId()).orElse(null);
        if (card == null || !Objects.equals(card.getUser().getId(), requestDTO.getUserId())) {
            throw new AccessDeniedException("Card does not belong to user");
        } else {
            if (requestDTO.getBlockStatus() == CardBlockRequest.BlockStatus.APPROVED) {
                card.setStatus(Card.STATUS.BLOCKED);
            }
            cardRepository.save(card);
        }
    }

    public List<BlockRequestFromUser> getBlockRequests() {
        return cardBlockRequestRepository.findAll().stream()
                .map(this::convertDTO).collect(Collectors.toList());
    }
}
