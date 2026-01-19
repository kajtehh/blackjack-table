package dev.kajteh.blackjack.bet;

import dev.kajteh.blackjack.table.TableResult;
import org.jetbrains.annotations.NotNull;

public record AmountBet(double value) implements Bet<Double> {

    public static AmountBet of(final double value) {
        return new AmountBet(value);
    }

    @Override
    public @NotNull Double result(@NotNull TableResult result) {
        return this.value * result.payoutMultiplier();
    }

    @Override
    public Bet<Double> createDouble() {
        return new AmountBet(this.value * 2);
    }
}
