package com.example.bankcards.service;

import com.example.bankcards.dto.cards.CardDTO;
import com.example.bankcards.dto.cards.CreateCardDTO;
import com.example.bankcards.dto.cards.EditCardDTO;
import com.example.bankcards.dto.cards.TransferDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.MaskCardNumber;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
//create block activate delete see all cards - admin
// user sees their cards, requests block, makes transactions, checks balance

@AllArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public List<Card> getCardsListFiltered(Long userId, Card.STATUS status) { //admin
        if (userId != null && status != null) {
            return cardRepository.getCardByUserIdAndStatus(userId, status);
        } else if (status != null) {
            return cardRepository.getCardByStatus(status);
        } else if (userId != null) {
            return cardRepository.getCardsByUserId(userId);
        }
        return cardRepository.findAll();
    }

    public void createCard(CreateCardDTO createCardDTO) { //admin
        Card card = new Card();
        User owner = userRepository.getReferenceById(createCardDTO.getOwnerId());
        card.setUser(owner);
        card.setCardNumber(createCardDTO.getCardNumber());
        card.setExpirationDate(createCardDTO.getExpirationDate());
        card.setStatus(Card.STATUS.ACTIVE);
        card.setBalance(100.0); // пусть будет изначальная сумма для тестов
        cardRepository.save(card);
    }

    public void deleteCard(Long id) { //admin
        cardRepository.deleteById(id);
    }

    public void updateCard(Long id, EditCardDTO editCardDTO) { //admin
        Card card = cardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Карта с id=" + id + " не найдена"));
        if (card != null) {
            if (editCardDTO.getStatus() != null) {
                card.setStatus(editCardDTO.getStatus());
            }
            if (editCardDTO.getExpirationDate() != null) {
                card.setExpirationDate(editCardDTO.getExpirationDate());
            }
            if (editCardDTO.getBalance() != null) {
                card.setBalance(editCardDTO.getBalance());
            }
            cardRepository.save(card);
        }
    }

    public List<CardDTO> getCardsByUser(Long userId, Card.STATUS status) {
        if (status != null) {
            return cardRepository.getCardByUserIdAndStatus(userId, status)
                    .stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        return cardRepository.getCardsByUserId(userId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public double getBalance(Long userId, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("Карта с id=" + cardId + " не найдена"));;
        assert card != null;
        if (Objects.equals(card.getUser().getId(), userId)) {
            return card.getBalance();
        } else {
            throw new AccessDeniedException("Карта не принадлежит пользователю");
        }
    }

    public void transferMoney(Long userId, TransferDTO transferDTO) {
        Card cardFrom = cardRepository.findById(transferDTO.getFromCardId()).orElseThrow(() -> new EntityNotFoundException("No such card to draw money from"));
        Card cardTo = cardRepository.findById(transferDTO.getToCardId()).orElseThrow(() -> new EntityNotFoundException("No such card to transfer money to"));;
        if (!(cardFrom.getUser().getId().equals(userId)
                && cardTo.getUser().getId().equals(userId))) {
            throw new AccessDeniedException("Карта не принадлежит пользователю");
        }
        if (!cardFrom.getStatus().equals(Card.STATUS.ACTIVE) || !cardTo.getStatus().equals(Card.STATUS.ACTIVE)) {
            throw new IllegalStateException("Одна из карт заблокирована");
        }
        if (cardFrom.getBalance() < transferDTO.getAmount()) {
            throw new IllegalStateException("Недостаточно средств на карте");
        }
        cardFrom.setBalance(cardFrom.getBalance() - transferDTO.getAmount());
        cardTo.setBalance(cardTo.getBalance() + transferDTO.getAmount());
        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);
        System.out.println("Operation successful");

    }

    public Page<CardDTO> getUserCardsPages(Long userId, String searchNumber, Pageable pageable) {
        if (searchNumber == null) {
            return cardRepository.findByUserId(userId, pageable).map(this::convertToDTO);
        }
        return cardRepository.findByUserIdAndCardNumberContaining(userId, searchNumber, pageable)
                .map(this::convertToDTO);
    }

    public CardDTO convertToDTO(Card card) {
        return new CardDTO(card.getUser().getId(), card.getId(), MaskCardNumber.maskNumber(card),
                card.getExpirationDate(), card.getStatus(), card.getBalance());
    }
}
