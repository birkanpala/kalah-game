package com.backbase.kalahgame.model;

import lombok.Getter;

import static com.backbase.kalahgame.constants.Constants.*;

@Getter
public enum Player {

    PLAYER_1(START_INDEX_PLAYER_1, END_INDEX_PLAYER_1, KALAH_INDEX_PLAYER_1),
    PLAYER_2(START_INDEX_PLAYER_2, END_INDEX_PLAYER_2, KALAH_INDEX_PLAYER_2);

    private final int start;

    private final int end;

    private final int kalah;

    Player(int start, int end, int kalah) {
        this.start = start;
        this.end = end;
        this.kalah = kalah;
    }

    public static Player byPitId(int pitId) {
        if (pitId >= PLAYER_1.start && pitId <= PLAYER_1.end) {
            return PLAYER_1;
        }
        if (pitId >= PLAYER_2.start && pitId <= PLAYER_2.end) {
            return PLAYER_2;
        }
        throw new IllegalArgumentException("Invalid pitId " + pitId);
    }

    public boolean ownsPit(int pitId) {
        return pitId >= this.start && pitId <= this.end;
    }

    public Player opponent() {
        return this == PLAYER_1 ? PLAYER_2 : PLAYER_1;
    }

}
