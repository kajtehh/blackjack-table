package dev.kajteh.blackjack.bet;

import dev.kajteh.blackjack.table.TableResult;
import org.jetbrains.annotations.NotNull;

public record ValueBet(double value) implements Bet<Double> {

    public static ValueBet of(final double value) {
        return new ValueBet(value);
    }

    @Override
    public @NotNull Double result(@NotNull TableResult result) {
        return this.value * result.payoutMultiplier();
    }

    @Override
    public Bet<Double> createDouble() {
        return new ValueBet(this.value * 2);
    }
}
