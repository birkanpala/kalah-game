package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IdentifyPlayerActionTest {

    private Action identifyPlayerAction;

    @BeforeEach
    public void init() {
        //given
        identifyPlayerAction = new IdentifyPlayerAction();
    }

    @Test
    @DisplayName("Should identify player by pit id")
    void shouldIdentifyPlayerByPitId() {

        //given
        Game game = new Game();

        //when
        identifyPlayerAction.execute(game, 1);

        //then
        assertThat(game.getPlayer(), equalTo(Player.PLAYER_1));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when pit id is invalid")
    void shouldThrowIllegalArgumentExceptionWhenPitIdIsInvalid() {

        //when, then
        assertThrows(IllegalArgumentException.class,
                () -> identifyPlayerAction.execute(new Game(), 0)
        );
    }

}