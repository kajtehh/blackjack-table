package dev.kajteh.blackjack.card;

import org.jetbrains.annotations.NotNull;

public record Card(@NotNull CardRank rank, @NotNull CardSuit suit) {

    @Override
    public @NotNull String toString() {
        return this.rank.value() <= 10 && this.rank != CardRank.ACE
                ? this.rank.value() + " " + this.suit.symbol()
                : this.rank.name() + " " + this.suit.symbol();
    }

    public int value() {
        return this.rank.value();
    }
}
