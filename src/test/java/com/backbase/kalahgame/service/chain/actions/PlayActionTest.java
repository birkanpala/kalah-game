package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Board;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Player;
import com.backbase.kalahgame.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PlayActionTest {

    private Action playAction;

    @BeforeEach
    public void init() {
        //given
        playAction = new PlayAction();
    }

    @Test
    @DisplayName("Should sow seeds")
    void shouldSowSeeds() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_1);
        game.start();

        //when
        playAction.execute(game, 1);

        //then
        Board board = game.getBoard();

        assertThat("pit 1 is empty", board.isEmpty(1));
        for (int i = 2; i < 7; i++) {
            assertThat(board.getSeedCount(i), equalTo(7));
        }
        assertThat(board.getSeedCount(7), equalTo(1));
    }


    @Test
    @DisplayName("Should get another turn when last seed goes to own kalah")
    void shouldGetAnotherTurnWhenLastSeedGoesToOwnKalah() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_1);
        game.start();

        //when
        playAction.execute(game, 1);

        //then
        assertThat(game.getStatus(), equalTo(Status.TURN_PLAYER_1));
    }


    @Test
    @DisplayName("Should change turn when last seed does not go to own kalah")
    void shouldChangeTurnWhenLastSeedDoesNotGoToOwnKalah() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_1);
        game.start();

        //when
        playAction.execute(game, 5);

        //then
        assertThat(game.getStatus(), equalTo(Status.TURN_PLAYER_2));
    }

    @Test
    @DisplayName("Should not sow to opponent's kalah")
    void shouldNotSowToKalahOfOpponent() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_1);
        game.start();

        Board board = game.getBoard();
        int[] pits = {1, 0, 8, 8, 8, 8, 2, 0, 8, 7, 7, 7, 7, 1};
        ReflectionTestUtils.setField(board, "pits", pits);

        //when
        playAction.execute(game, 6);

        //then
        assertThat("pit 6 is empty", board.isEmpty(6));
        assertThat(board.getSeedCount(7), equalTo(3)); //own kalah
        assertThat(board.getSeedCount(14), equalTo(1)); //opponent's kalah
        assertThat(board.getSeedCount(1), equalTo(2));
    }

    @Test
    @DisplayName("Should collect opponent's seeds when last seed goes to empty pit")
    void shouldCollectSeedsOfOpponentWhenLastSeedGoesToEmptyPit() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_2);
        game.start();

        Board board = game.getBoard();
        int[] pits = {0, 3, 11, 11, 2, 3, 5, 4, 10, 9, 9, 0, 1, 4};
        ReflectionTestUtils.setField(board, "pits", pits);

        //when
        playAction.execute(game, 8);

        //then
        assertThat("pit 8 is empty", board.isEmpty(8));
        assertThat("pit 2 is empty", board.isEmpty(2));
        assertThat(board.getSeedCount(14), equalTo(8)); // own kalah
    }

}