package dev.kajteh.blackjack.table;

import org.jetbrains.annotations.NotNull;

public enum TableAction {
    HIT {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            return table.state() == TableState.PLAYER_TURN;
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            table.playerHand().addCard(table.deck().draw());
            if (table.playerHand().isBust()) table.finish();
        }
    },
    STAND {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            return table.state() == TableState.PLAYER_TURN;
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            table.state(TableState.DEALER_TURN);
        }
    },
    DOUBLE_DOWN {
        @Override
        public boolean isPossible(@NotNull Table<?, ?> table) {
            return table.state() == TableState.PLAYER_TURN && table.playerHand().cards().size() == 2;
        }

        @Override
        public void perform(@NotNull Table<?, ?> table) {
            table.doubleBet();
            table.playerHand().addCard(table.deck().draw());
            
            if (table.playerHand().isBust()) table.finish();
            else STAND.perform(table);
        }
    };

    public abstract boolean isPossible(@NotNull Table<?, ?> table);
    public abstract void perform(@NotNull Table<?, ?> table);
}