package com.backbase.kalahgame.model;

import lombok.ToString;

import javax.persistence.Embeddable;
import java.util.Map;
import java.util.stream.IntStream;

import static com.backbase.kalahgame.constants.Constants.*;
import static com.backbase.kalahgame.model.Player.PLAYER_1;
import static com.backbase.kalahgame.model.Player.PLAYER_2;
import static java.util.Arrays.fill;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Embeddable
@ToString
public class Board {

    private final int[] pits;

    /**
     * Creates a new board
     */
    Board() {

        pits = new int[TOTAL_NUMBER_OF_PITS];

        init(PLAYER_1);
        init(PLAYER_2);
    }

    private void init(Player player) {
        fill(pits, player.getStart() - 1, player.getEnd(), NUMBER_OF_SEEDS);
    }

    /**
     * Gets number of seeds in the pit.
     *
     * @param pitId pit Id
     * @return number of seeds
     */
    public int getSeedCount(int pitId) {

        rangeCheck(pitId);

        return pits[pitId - 1];
    }

    /**
     * Sows one seed to pit.
     *
     * @param pitId pit Id
     */
    public void sowSeed(int pitId) {

        rangeCheck(pitId);

        pits[pitId - 1]++;
    }

    /**
     * Sows seeds by given amount to pit.
     *
     * @param pitId  pit Id
     * @param amount amount
     */
    public void sowSeeds(int pitId, int amount) {

        rangeCheck(pitId);

        pits[pitId - 1] += amount;
    }

    /**
     * Clears the pit.
     *
     * @param pitId pit Id
     */
    public void clearPit(int pitId) {

        rangeCheck(pitId);

        pits[pitId - 1] = 0;
    }

    /**
     * Checks if pit is empty.
     *
     * @param pitId pit Id
     * @return {@code true} if pit is empty, otherwisee {@code false}
     */
    public boolean isEmpty(int pitId) {

        rangeCheck(pitId);

        return pits[pitId - 1] == 0;
    }

    /**
     * Checks if any side of the board is empty.
     *
     * @return {@code true}  if any side is empty, otherwise {@code false}
     */
    public boolean isAnySideEmpty() {

        return isOutOfSeeds(PLAYER_1) || isOutOfSeeds(PLAYER_2);
    }

    private boolean isOutOfSeeds(Player player) {
        return IntStream.range(player.getStart(), player.getKalah()).allMatch(this::isEmpty);
    }

    private void rangeCheck(int pitId) {

        if (pitId < START_INDEX_PLAYER_1 || pitId > TOTAL_NUMBER_OF_PITS) {
            throw new IllegalArgumentException("Invalid pitId '" + pitId + "'");
        }
    }

    /**
     * Collects all the seeds to related players kalah.
     */
    void collectSeeds() {

        collectSeeds(PLAYER_1);
        collectSeeds(PLAYER_2);
    }

    private void collectSeeds(Player player) {

        int seeds = 0;

        for (int i = player.getStart(); i <= player.getEnd(); i++) {
            seeds += getSeedCount(i);
            clearPit(i);
        }
        sowSeeds(player.getKalah(), seeds);
    }

    /**
     * Generates the status of the board.
     *
     * @return Status map.
     */
    public Map<Integer, String> getStatus() {

        return IntStream
                .range(START_INDEX_PLAYER_1, TOTAL_NUMBER_OF_PITS + 1)
                .boxed()
                .collect(toMap(identity(), i -> Integer.toString(pits[i - 1])));
    }
}
