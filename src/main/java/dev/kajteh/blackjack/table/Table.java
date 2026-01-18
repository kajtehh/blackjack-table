package dev.kajteh.blackjack.table;

import dev.kajteh.blackjack.bet.Bet;
import dev.kajteh.blackjack.table.component.Deck;
import dev.kajteh.blackjack.table.component.Hand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class Table<STAKE, BET extends Bet<STAKE>> {

    private BET bet;
    private final BiConsumer<TableResult, STAKE> onFinish;

    private final Deck deck;
    private final Hand dealerHand, playerHand;

    private TableState state;

    public Table(@NotNull BET bet, @NotNull BiConsumer<TableResult, STAKE> onFinish) {
        this.bet = bet;
        this.onFinish = onFinish;

        this.deck = new Deck();
        this.deck.shuffle();

        this.dealerHand = new Hand();
        this.playerHand = new Hand();

        this.start();
    }

    private void start() {
        this.state = TableState.INITIAL_DEALING;

        for (int i = 0; i < 2; i++) {
            this.playerHand.addCard(this.deck.draw());
            this.dealerHand.addCard(this.deck.draw());
        }

        if (this.playerHand.isBlackjack() || this.dealerHand.isBlackjack()) {
            this.finish();
            return;
        }

        this.state = TableState.PLAYER_TURN;
    }

    public void play(@NotNull TableAction action) {
        if (!action.isPossible(this)) {
            throw new IllegalStateException("Action " + action.name() + " is currently blocked.");
        }
        action.perform(this);
    }

    public List<TableAction> availableActions() {
        return Arrays.stream(TableAction.values())
                .filter(action -> action.isPossible(this))
                .toList();
    }

    public boolean dealerStep() {
        if (this.state != TableState.DEALER_TURN) return false;

        if (this.dealerHand.score() < 17) {
            this.dealerHand.addCard(this.deck.draw());

            if (this.dealerHand.isBust()) this.finish();
            return true;
        }

        //this.finish();
        return false;
    }

    public void finish() {
        this.state = TableState.GAME_OVER;

        final var result = this.calculateResult();
        this.onFinish.accept(result, this.bet.result(result));
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

    public TableState state() {
        return this.state;
    }

    public Hand playerHand() {
        return this.playerHand;
    }

    public Hand dealerHand() {
        return this.dealerHand;
    }

    public Deck deck() {
        return this.deck;
    }

    public void state(final @NotNull TableState state) {
        this.state = state;
    }
}