package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.backbase.kalahgame.constants.Constants.ORDER_3;
import static com.backbase.kalahgame.model.Status.CREATED;

@Component
@Order(ORDER_3)
@Slf4j
public class StartGameAction implements Action {

    @Override
    public void execute(final Game game, final int pitId) {

        if (game.getStatus() == CREATED) {

            game.start();

            log.info("Game [{}] is started by {}", game.getId(), game.getPlayer());
        }
    }
}
