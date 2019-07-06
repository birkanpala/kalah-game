package com.backbase.kalahgame.model;

public enum Status {

    CREATED,
    TURN_PLAYER_1,
    TURN_PLAYER_2,
    FINISHED;

    public Status nextTurn() {
        return this == TURN_PLAYER_1 ? TURN_PLAYER_2 : TURN_PLAYER_1;
    }

    public static Status byPlayer(Player player) {
        return player == Player.PLAYER_1 ? TURN_PLAYER_1 : TURN_PLAYER_2;
    }
}
