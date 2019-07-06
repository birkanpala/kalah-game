package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Board;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Player;
import com.backbase.kalahgame.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.backbase.kalahgame.constants.Constants.*;

@Component
@Order(ORDER_4)
@Slf4j
public class PlayAction implements Action {

    @Override
    public void execute(final Game game, final int pitId) {

        log.debug("Game [{}] {} is playing for pit {}", game.getId(), game.getPlayer(), pitId);

        game.play(() -> play(game, pitId));

        log.info("Game [{}] {} played. Status {}", game.getId(), game.getPlayer(), game.getStatus());
    }

    private Status play(final Game game, final int pitId) {

        final Player player = game.getPlayer();

        final int lastPit = sowSeeds(game.getBoard(), player, pitId);

        if (lastPit == player.getKalah()) {
            return game.getStatus();
        }

        return game.getStatus().nextTurn();
    }

    private int sowSeeds(final Board board, final Player player, final int pitId) {

        final int seeds = board.getSeedCount(pitId);

        board.clearPit(pitId);

        int nextPit = pitId;

        for (int i = 0; i < seeds; i++) {

            nextPit = getNextPit(player, nextPit);

            board.sowSeed(nextPit);
        }

        handleLastSeed(board, player, nextPit);

        return nextPit;
    }

    private void handleLastSeed(final Board board, final Player player, final int pit) {

        final int oppositePit = getOppositePit(pit);

        if (board.getSeedCount(pit) == 1 && player.ownsPit(pit) && !board.isEmpty(oppositePit)) {

            int totalSeeds = board.getSeedCount(pit) + board.getSeedCount(oppositePit);

            board.clearPit(pit);
            board.clearPit(oppositePit);
            board.sowSeeds(player.getKalah(), totalSeeds);
        }
    }

    private int getNextPit(final Player player, final int pitId) {

        int nextPit = pitId + 1;

        if (nextPit == player.getKalah()) {
            return nextPit;
        }

        if (nextPit == player.opponent().getKalah()) {
            nextPit++;
        }

        return nextPit % TOTAL_NUMBER_OF_PITS;
    }

    private int getOppositePit(final int pitId) {
        return TOTAL_NUMBER_OF_PITS - pitId;
    }

}
