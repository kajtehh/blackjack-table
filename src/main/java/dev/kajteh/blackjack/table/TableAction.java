package dev.kajteh.blackjack.table;

import dev.kajteh.blackjack.table.component.Participant;
import org.jetbrains.annotations.NotNull;

public enum TableAction {
    HIT {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            return table.state() == TableState.PLAYER_TURN;
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            table.dealCard(Participant.PLAYER, true);
        }
    },
    STAND {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            return table.state() == TableState.PLAYER_TURN;
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            table.updateState(TableState.DEALER_TURN);
        }
    },
    DOUBLE_DOWN {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            return table.state() == TableState.PLAYER_TURN && table.hand(Participant.PLAYER).cards().size() == 2;
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            table.doubleBet();
            table.dealCard(Participant.PLAYER, true);

            if(!table.hand(Participant.PLAYER).isBust()) {
                STAND.perform(table);
            }
        }
    },
    SPLIT {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            final var cards = table.playerHand().cards();
            return table.state() == TableState.PLAYER_TURN
                    && cards.size() == 2
                    && cards.get(0).rank() == cards.get(1).rank();
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            // todo
        }
    };

    public abstract boolean isPossible(@NotNull Table<?, ?> table);
    public abstract void perform(@NotNull Table<?, ?> table);
}