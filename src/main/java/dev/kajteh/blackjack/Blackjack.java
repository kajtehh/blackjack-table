package dev.kajteh.blackjack;

import dev.kajteh.blackjack.bet.Bet;
import dev.kajteh.blackjack.table.Table;
import org.jetbrains.annotations.NotNull;

public final class Blackjack {

    private Blackjack() {}

    public static <STAKE, BET extends Bet<STAKE>> Table<STAKE, BET> openTable(@NotNull BET bet) {
        return new Table<>(bet);
    }
}
