package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Player;
import com.backbase.kalahgame.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class StartGameActionTest {

    private Action startGameAction;

    @BeforeEach
    public void init() {
        //given
        startGameAction = new StartGameAction();
    }

    @Test
    @DisplayName("Should start game when it is in created state")
    void shouldStartGame() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_2);

        //when
        startGameAction.execute(game, 1);

        //then
        assertThat(game.getStatus(), equalTo(Status.TURN_PLAYER_2));
    }

    @Test
    @DisplayName("Should not change status when it is not in created state")
    void shouldNotChangeStateWhenNotCreated() {

        //given
        Game game = new Game();
        game.setPlayer(Player.PLAYER_2);
        game.start();

        //when
        startGameAction.execute(game, 1);

        //then
        assertThat(game.getStatus(), equalTo(Status.TURN_PLAYER_2));
    }

}