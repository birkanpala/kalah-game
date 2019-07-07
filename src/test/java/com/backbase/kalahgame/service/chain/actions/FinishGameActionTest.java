package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Board;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

class FinishGameActionTest {

    private Action finishGameAction;

    @BeforeEach
    public void init() {
        //given
        finishGameAction = new FinishGameAction();
    }

    @Test
    @DisplayName("Should finish when one side is Empty")
    void shouldFinishWhenOneSideIsEmpty() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_1);
        game.start();

        Board board = game.getBoard();
        int[] pits = {1, 0, 8, 0, 0, 8, 20, 0, 0, 0, 0, 0, 0, 35};
        ReflectionTestUtils.setField(board, "pits", pits);

        //when
        finishGameAction.execute(game, 13);

        //then
        assertThat("finished", game.isFinished());
    }

    @Test
    @DisplayName("Should not finish when both sides not empty")
    void shouldNotFinishWhenBothSidesNotEmpty() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_1);
        game.start();

        //when
        finishGameAction.execute(game, 1);

        //then
        assertThat("finished", not(game.isFinished()));
    }

}