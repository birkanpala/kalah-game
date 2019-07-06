package com.backbase.kalahgame.service.chain;

import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.service.chain.actions.Action;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameActionExecutor {

    private final List<Action> actions;

    @PostConstruct
    public void init() {
        actions.sort(AnnotationAwareOrderComparator.INSTANCE);
    }

    /**
     * Executes list of actions for the game and pit.
     *
     * @param game  Game
     * @param pitId pit Id
     */
    public void execute(final Game game, final int pitId) {

        actions.forEach(action -> action.execute(game, pitId));
    }

}
