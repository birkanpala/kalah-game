package com.backbase.kalahgame.service.chain.actions;

import com.backbase.kalahgame.model.Game;

public interface Action {

    void execute(final Game game, final int pitId);
}
