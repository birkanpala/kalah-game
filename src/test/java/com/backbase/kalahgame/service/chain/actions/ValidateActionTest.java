package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Player;
import com.backbase.kalahgame.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateActionTest {

    private Action validateAction;

    @BeforeEach
    public void init() {
        //given
        validateAction = new ValidateAction();
    }

    @Test
    @DisplayName("Should throw IllegalStateException when game is finished")
    void shouldThrowIllegalStateExceptionWhenGameIsFinished() {

        //given
        Game game = new Game();
        game.finish();

        //when, then
        assertThrows(IllegalStateException.class,
                () -> validateAction.execute(game, 1)
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when wrong turn")
    void shouldThrowIllegalArgumentExceptionWhenWrongTurn() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_2);
        ReflectionTestUtils.setField(game, "status", Status.TURN_PLAYER_1);

        //when, then
        assertThrows(IllegalArgumentException.class,
                () -> validateAction.execute(game, 1)
        );
    }

    @Test
    @DisplayName("Should check empty pit")
    void shouldCheckEmptyPit() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_1);
        game.start();
        game.getBoard().clearPit(1);

        //when, then
        assertThrows(IllegalArgumentException.class,
                () -> validateAction.execute(game, 1)
        );
    }


}