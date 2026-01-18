package dev.kajteh.blackjack.table.component;

import dev.kajteh.blackjack.card.Card;
import dev.kajteh.blackjack.card.CardRank;
import dev.kajteh.blackjack.card.CardSuit;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    private final List<Card> cards = Arrays.stream(CardSuit.values())
            .flatMap(suit -> Arrays.stream(CardRank.values())
                    .map(rank -> new Card(rank, suit)))
            .collect(Collectors.toCollection(LinkedList::new));

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty! Shuffle a new one.");
        }
        return this.cards.removeFirst();
    }
}
