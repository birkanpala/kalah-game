package com.backbase.kalahgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.IntStream;

import static com.backbase.kalahgame.model.Player.PLAYER_1;
import static com.backbase.kalahgame.model.Player.PLAYER_2;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    private Board board;

    @BeforeEach
    public void init() {
        //given
        board = new Board();
    }

    @Test
    @DisplayName("Should get seed count by pit id")
    void shouldGetSeedCount() {

        //when
        int pit1 = board.getSeedCount(1);
        int kalah1 = board.getSeedCount(7);

        //then
        assertThat(pit1, equalTo(6));
        assertThat(kalah1, equalTo(0));
    }

    @Test
    @DisplayName("Should sow single seed to a pit")
    void shouldSowSingleSeedToPit() {

        //when
        board.sowSeed(1);
        int pit1 = board.getSeedCount(1);

        //then
        assertThat(pit1, equalTo(7));
    }

    @Test
    @DisplayName("Should sow multiple seeds to a pit")
    void shouldSowMultipleSeedsToPit() {

        //when
        board.sowSeeds(1, 2);
        int pit1 = board.getSeedCount(1);

        //then
        assertThat(pit1, equalTo(8));
    }

    @Test
    @DisplayName("Should clear a pit")
    void shouldClearPit() {

        //when
        board.clearPit(1);
        int pit1 = board.getSeedCount(1);

        //then
        assertThat(pit1, equalTo(0));
    }

    @Test
    @DisplayName("Pit should be empty when cleared")
    void pitShouldBeEmptyWhenCleared() {

        //when
        board.clearPit(1);
        boolean empty = board.isEmpty(1);

        //then
        assertThat("isEmpty", empty);
    }

    @Test
    @DisplayName("Any side should be empty when a player_1 has no seeds")
    void anySideShouldBeEmptyWhenPlayer1HasNoSeeds() {

        //when
        //clear pits of player_1 [1,6]
        IntStream.range(PLAYER_1.getStart(), PLAYER_1.getKalah())
                .forEach(i -> board.clearPit(i));

        boolean anySideEmpty = board.isAnySideEmpty();

        //then
        assertThat("anySideEmpty", anySideEmpty);
    }

    @Test
    @DisplayName("Any side should be empty when a player_2 has no seeds")
    void anySideShouldBeEmptyWhenPlayer2HasNoSeeds() {

        //when
        //clear pits of player_2 [8,13]
        IntStream.range(PLAYER_2.getStart(), PLAYER_2.getKalah())
                .forEach(i -> board.clearPit(i));

        boolean anySideEmpty = board.isAnySideEmpty();

        //then
        assertThat("anySideEmpty", anySideEmpty);
    }

    @Test
    @DisplayName("Any side should be empty when a player has no seeds")
    void anySideShouldBeEmptyWhenHasNoSeeds() {

        boolean anySideEmpty = board.isAnySideEmpty();

        //then
        assertThat("anySideEmpty", not(anySideEmpty));
    }

    @Test
    @DisplayName("Should collect all seeds to kalahs of players")
    void shouldCollectAllSeedsToKalah() {

        //when
        board.collectSeeds();

        //then
        assertThat(board.getSeedCount(PLAYER_1.getKalah()), equalTo(36));
        assertThat(board.getSeedCount(PLAYER_2.getKalah()), equalTo(36));
    }

    @Test
    @DisplayName("Should check pit id range")
    void shouldCheckPitIdRange() {

        //when, then
        assertThrows(IllegalArgumentException.class,
                () -> board.getSeedCount(0)
        );
    }

    @Test
    @DisplayName("Should get status")
    void shouldGetStatus() {

        //when
        Map<String, String> status = board.getStatus();
        //then
        assertThat(status, hasEntry(equalTo("1"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("2"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("3"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("4"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("5"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("6"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("7"), equalTo("0")));

        assertThat(status, hasEntry(equalTo("8"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("9"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("10"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("11"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("12"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("13"), equalTo("6")));
        assertThat(status, hasEntry(equalTo("14"), equalTo("0")));
    }
}