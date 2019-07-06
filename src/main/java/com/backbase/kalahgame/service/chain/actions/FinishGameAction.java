package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.backbase.kalahgame.constants.Constants.ORDER_5;

@Component
@Order(ORDER_5)
@Slf4j
public class FinishGameAction implements Action {

    @Override
    public void execute(final Game game, final int pitId) {

        if (game.getBoard().isAnySideEmpty()) {

            game.finish();

            log.info("Game [{}] is finished!", game.getId());
        }
    }
}
