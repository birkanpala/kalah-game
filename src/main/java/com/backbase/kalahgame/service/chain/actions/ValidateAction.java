package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.backbase.kalahgame.constants.Constants.ORDER_2;

@Component
@Order(ORDER_2)
@Slf4j
public class ValidateAction implements Action {

    @Override
    public void execute(final Game game, final int pitId) {

        log.debug("Game [{}] validating move!", game.getId());

        checkGameStatus(game);

        checkTurn(game);

        checkEmptyPit(game, pitId);

        log.debug("Game [{}] valid move pitId {}", game.getId(), pitId);
    }

    private void checkGameStatus(Game game) {

        if (game.isFinished()) {
            throw new IllegalStateException("Game is finished!");
        }
    }

    private void checkTurn(Game game) {

        if (game.getStatus() == Status.CREATED) {
            log.debug("Game [{}] Any player can start game!", game.getId());
            return;
        }

        if (game.getStatus() != Status.byPlayer(game.getPlayer())) {
            throw new IllegalArgumentException("This turn belongs to your opponent!");
        }
    }

    private void checkEmptyPit(Game game, int pitId) {

        if (game.getBoard().isEmpty(pitId)) {
            throw new IllegalArgumentException("Pit is empty!");
        }
    }

}
