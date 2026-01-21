package dev.kajteh.blackjack.table.component;

import dev.kajteh.blackjack.card.Card;
import dev.kajteh.blackjack.card.CardRank;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private static final int BLACKJACK_SCORE = 21;
    private static final int INITIAL_HAND_SIZE = 2;

    private final List<Card> cards = new ArrayList<>();

    public void addCard(@NotNull Card card) {
        this.cards.add(card);
    }

    public int score() {
        int score = 0;
        int aces = 0;

        for (final var card : this.cards) {
            score += card.value();
            if (card.rank() == CardRank.ACE) {
                aces++;
            }
        }

        while (score > BLACKJACK_SCORE && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }


    public boolean isBust() {
        return this.score() > BLACKJACK_SCORE;
    }

    public boolean isSplittable() {
        return this.isInitial() &&
                this.cards.get(0).rank() == this.cards.get(1).rank();
    }

    public boolean isSafe() {
        return !this.isBust();
    }

    public boolean isInitial() {
        return this.cards.size() == INITIAL_HAND_SIZE;
    }

    public boolean isBlackjack() {
        return this.cards.size() == 2 && this.score() == BLACKJACK_SCORE;
    }

    public List<Card> cards() {
        return this.cards;
    }
}
