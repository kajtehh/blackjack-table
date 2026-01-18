package dev.kajteh.blackjack;

import dev.kajteh.blackjack.bet.Bet;
import dev.kajteh.blackjack.table.Table;
import dev.kajteh.blackjack.table.TableResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public final class Blackjack {

    private Blackjack() {}

    public static <STAKE, BET extends Bet<STAKE>> Table<STAKE, BET> classicTable(
            @NotNull BET bet,
            @NotNull BiConsumer<TableResult, STAKE> onFinish
    ) {
        return new Table<>(bet, onFinish);
    }
}
