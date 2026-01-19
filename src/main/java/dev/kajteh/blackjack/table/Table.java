package dev.kajteh.blackjack.table;

import dev.kajteh.blackjack.bet.Bet;
import dev.kajteh.blackjack.card.Card;
import dev.kajteh.blackjack.table.component.Deck;
import dev.kajteh.blackjack.table.component.Hand;
import dev.kajteh.blackjack.table.component.Participant;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Table<STAKE, BET extends Bet<STAKE>> {

    private BET bet;

    private final Deck deck;
    private final Hand dealerHand, playerHand;

    private TableState state;
    private boolean dealerRevealed;

    private TableCallback<STAKE> callback = new TableCallback<>() {};

    public Table(@NotNull BET bet) {
        this.bet = bet;

        this.deck = new Deck();
        this.deck.shuffle();

        this.dealerHand = new Hand();
        this.playerHand = new Hand();
    }

    public Table<STAKE, BET> watch(@NotNull TableCallback<STAKE> callback) {
        this.callback = callback;
        return this;
    }

    public void start() {
        this.updateState(TableState.INITIAL_DEALING);

        for (int i = 0; i < 2; i++) {
            this.dealCard(Participant.PLAYER, true);
            this.dealCard(Participant.DEALER, i == 0);
        }

        if (this.playerHand.isBlackjack() || this.dealerHand.isBlackjack()) {
            this.finish();
            return;
        }

        this.updateState(TableState.PLAYER_TURN);
    }

    public void play(@NotNull TableAction action) {
        if (!action.isPossible(this)) {
            throw new IllegalStateException("Action " + action.name() + " is currently blocked.");
        }

        this.callback.onAction(action);
        action.perform(this);
    }

    public List<TableAction> availableActions() {
        return Arrays.stream(TableAction.values())
                .filter(action -> action.isPossible(this))
                .toList();
    }

    public boolean dealerStep() {
        if (this.state != TableState.DEALER_TURN) return false;

        if(!this.dealerRevealed) {
            this.revealDealerCard();
            return true;
        }

        if (this.dealerHand.score() < 17) {
            this.dealCard(Participant.DEALER, true);
            return true;
        }

        this.finish();
        return false;
    }

    private void revealDealerCard() {
        if (this.dealerRevealed) return;
        this.dealerRevealed = true;

        final Card hiddenCard = this.dealerHand.cards().get(1);

        this.callback.onDeal(Participant.DEALER, hiddenCard, true);
    }

    public void finish() {
        this.revealDealerCard();
        this.updateState(TableState.GAME_OVER);

        final var result = this.calculateResult();
        this.callback.onFinish(result, this.bet.result(result));
    }

    @SuppressWarnings("unchecked")
    void doubleBet() {
        this.bet = (BET) this.bet.createDouble();
    }

    private TableResult calculateResult() {
        if (this.playerHand.isBust()) return TableResult.PLAYER_BUST;
        if (this.dealerHand.isBust()) return TableResult.DEALER_BUST;

        if (this.playerHand.isBlackjack()) return this.dealerHand.isBlackjack()
                ? TableResult.PUSH
                : TableResult.BLACKJACK;

        return switch (Integer.compare(this.playerHand.score(), this.dealerHand.score())) {
            case 1 -> TableResult.WIN;
            case -1 -> TableResult.LOSS;
            default -> TableResult.PUSH;
        };
    }

    public boolean dealerRevealed() {
        return this.dealerRevealed;
    }

    public TableState state() {
        return this.state;
    }

    public boolean isGameOver() {
        return this.state == TableState.GAME_OVER;
    }

    public boolean isActive() {
        return !this.isGameOver();
    }

    public void updateState(@NotNull TableState state) {
        this.state = state;
        this.callback.onStateChange(state);
    }

    public Hand hand(@NotNull Participant participant) {
        return switch (participant) {
            case PLAYER -> this.playerHand;
            case DEALER -> this.dealerHand;
        };
    }

    public Hand playerHand() {
        return this.playerHand;
    }

    public Hand dealerHand() {
        return this.dealerHand;
    }

    public void dealCard(@NotNull Participant participant, boolean visible) {
        final var hand = this.hand(participant);
        final var drawnCard = this.deck.draw();

        hand.addCard(drawnCard);

        this.callback.onDeal(participant, drawnCard, visible);

        if(hand.isBust()) this.finish();
    }
}