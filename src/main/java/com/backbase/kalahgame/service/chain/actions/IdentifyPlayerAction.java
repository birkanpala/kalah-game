package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.backbase.kalahgame.constants.Constants.ORDER_1;

@Component
@Order(ORDER_1)
@Slf4j
public class IdentifyPlayerAction implements Action {

    @Override
    public void execute(final Game game, final int pitId) {

        game.setPlayer(Player.byPitId(pitId));

        log.info("Game [{}] Player is identified as {}", game.getId(), game.getPlayer());
    }

}
