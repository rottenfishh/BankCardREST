package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    public List<Card> getCardsByUserId(Long userId);
    public List<Card> getCardByUserIdAndStatus(Long userId, Card.STATUS status);
    public List<Card> getCardByStatus(Card.STATUS status);

}
