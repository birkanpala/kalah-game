package com.backbase.kalahgame.service.chain;

import com.backbase.kalahgame.service.chain.actions.Action;
import com.backbase.kalahgame.service.chain.actions.FinishGameAction;
import com.backbase.kalahgame.service.chain.actions.IdentifyPlayerAction;
import com.backbase.kalahgame.service.chain.actions.PlayAction;
import com.backbase.kalahgame.service.chain.actions.StartGameAction;
import com.backbase.kalahgame.service.chain.actions.ValidateAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;

@SpringBootTest
@ActiveProfiles("executor-test")
class GameActionExecutorTest {

    @Autowired
    private GameActionExecutor gameActionExecutor;

    @Test
    @DisplayName("Should sort actions by order")
    @SuppressWarnings("unchecked")
    void shouldSortActionsByOrder() {

        List<Action> actions = (List<Action>) ReflectionTestUtils.getField(gameActionExecutor, "actions");

        assertThat(actions, hasSize(5));
        assertThat(actions.get(0), instanceOf(IdentifyPlayerAction.class));
        assertThat(actions.get(1), instanceOf(ValidateAction.class));
        assertThat(actions.get(2), instanceOf(StartGameAction.class));
        assertThat(actions.get(3), instanceOf(PlayAction.class));
        assertThat(actions.get(4), instanceOf(FinishGameAction.class));
    }

}