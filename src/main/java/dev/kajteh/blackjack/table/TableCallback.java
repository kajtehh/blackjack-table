package dev.kajteh.blackjack.table;

import dev.kajteh.blackjack.card.Card;
import dev.kajteh.blackjack.table.component.Participant;
import org.jetbrains.annotations.NotNull;

public interface TableCallback<STAKE> {

    default void onAction(@NotNull TableAction action) {}

    default void onStateChange(@NotNull TableState state) {}

    default void onDeal(@NotNull Participant participant, @NotNull Card card, boolean visible) {}

    default void onFinish(@NotNull TableResult result, @NotNull STAKE payout) {}
}
