package dev.kajteh.blackjack.bet;

import dev.kajteh.blackjack.table.TableResult;
import org.jetbrains.annotations.NotNull;

public interface Bet<STAKE> {

    @NotNull STAKE result(@NotNull TableResult result);

    Bet<STAKE> createDouble();
}