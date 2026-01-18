package dev.kajteh.blackjack.table;

public enum TableResult {
    BLACKJACK(2.5),
    WIN(2.0),
    DEALER_BUST(2.0),
    PUSH(1.0),
    LOSS(0.0),
    PLAYER_BUST(0.0);

    private final double payoutMultiplier;

    TableResult(final double payoutMultiplier) {
        this.payoutMultiplier = payoutMultiplier;
    }

    public double payoutMultiplier() {
        return this.payoutMultiplier;
    }
}