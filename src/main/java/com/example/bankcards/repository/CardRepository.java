package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> getCardsByUserId(Long userId);
    List<Card> getCardByUserIdAndStatus(Long userId, Card.STATUS status);
    List<Card> getCardByStatus(Card.STATUS status);
    Page<Card> findByUserId(Long userId, Pageable pageable);
    Page<Card> findByUserIdAndCardNumberContaining(Long userId, String number, Pageable pageable);

}
