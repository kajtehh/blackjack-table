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
            return table.state() == TableState.PLAYER_TURN
                    && table.activeHand().isInitial()
                    && table.canDoubleBet();
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            table.doubleBet();
            table.dealCard(Participant.PLAYER, true);

            if(table.activeHand().isSafe()) {
                STAND.perform(table);
            }
        }
    },
    SPLIT {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            return table.state() == TableState.PLAYER_TURN && table.activeHand().isSplittable(); // todo add 4 hands limit
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            // todo
        }
    };

    public abstract boolean isPossible(@NotNull Table<?, ?> table);
    public abstract void perform(@NotNull Table<?, ?> table);
}