package com.backbase.kalahgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    private Game game;

    @BeforeEach
    public void init() {
        //given
        game = new Game();
    }

    @Test
    @DisplayName("Game should be created in CREATED state")
    void gameShouldBeCreatedInCREATEDState() {

        //when, then
        assertThat(game.getStatus(), equalTo(Status.CREATED));
    }

    @Test
    @DisplayName("Game should be started only in CREATED state")
    void gameShouldBeStartedOnlyInCREATEDState() {

        //when
        game.setPlayer(Player.PLAYER_1);
        game.start();

        //then
        assertThat(game.getStatus(), equalTo(Status.TURN_PLAYER_1));

        assertThrows(IllegalStateException.class,
                () -> game.start()
        );
    }

    @Test
    @DisplayName("Game should be finished")
    void gameShouldBeFinished() {

        //when
        game.finish();

        // then
        assertThat("finished", game.isFinished());
    }

    @Test
    @DisplayName("Game status should be finished when game ends")
    void gameStatusShouldBeFinishedWhenGameIsFinished() {

        //when
        game.finish();

        // then
        assertThat(game.getStatus(), equalTo(Status.FINISHED));
    }

    @Test
    @DisplayName("Game should be played with function")
    void gameShouldBePlayed() {

        //given
        game.setPlayer(Player.PLAYER_1);
        game.start();

        //when
        Supplier<Status> play = () -> Status.TURN_PLAYER_2;
        game.play(play);

        // then
        assertThat(game.getStatus(), equalTo(Status.TURN_PLAYER_2));
    }

}