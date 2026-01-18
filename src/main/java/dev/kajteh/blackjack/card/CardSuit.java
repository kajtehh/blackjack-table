package dev.kajteh.blackjack.card;

import org.jetbrains.annotations.NotNull;

public enum CardSuit {
    HEARTS("♥"),
    DIAMONDS("♦"),
    CLUBS("♣"),
    SPADES("♠");

    private final String symbol;

    CardSuit(final @NotNull String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return this.symbol;
    }
}
