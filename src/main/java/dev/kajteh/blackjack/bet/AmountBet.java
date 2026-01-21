package dev.kajteh.blackjack.bet;

import dev.kajteh.blackjack.table.TableResult;
import org.jetbrains.annotations.NotNull;

public record AmountBet(double value, boolean canDouble) implements Bet<Double> {

    public static AmountBet of(final double value, final boolean canDouble) {
        return new AmountBet(value, canDouble);
    }

    @Override
    public @NotNull Double result(@NotNull TableResult result) {
        return this.value * result.payoutMultiplier();
    }

    @Override
    public @NotNull Bet<Double> createDouble() {
        return new AmountBet(this.value * 2, this.canDouble);
    }

    @Override
    public boolean canDouble() {
        return this.canDouble;
    }
}
