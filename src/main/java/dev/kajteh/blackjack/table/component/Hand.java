package dev.kajteh.blackjack.table.component;

import dev.kajteh.blackjack.card.Card;
import dev.kajteh.blackjack.card.CardRank;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Hand {

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

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }

    public boolean isBust() {
        return this.score() > 21;
    }

    public boolean isBlackjack() {
        return this.cards.size() == 2 && this.score() == 21;
    }

    public List<Card> cards() {
        return this.cards;
    }
}
