package com.example.bankcards.util;

import com.example.bankcards.entity.Card;

public class MaskCardNumber {
    public static String maskNumber(Card card) {
        if (card.getCardNumber().length() != 16) {
            throw new IllegalStateException("Card number should be 16 characters");
        }
        String zvezda = "*";
        return zvezda.repeat(12)
                + card.getCardNumber().substring(12, 16);
    }
}
