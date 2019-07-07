package com.backbase.kalahgame.service;

import com.backbase.kalahgame.exception.ResourceNotFoundException;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.Status;
import com.backbase.kalahgame.repository.GameRepository;
import com.backbase.kalahgame.service.chain.GameActionExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameActionExecutor gameActionExecutor;

    @Test
    @DisplayName("Game should be created")
    void gameShouldBeCreated() {

        //given
        Game game = new Game();
        ReflectionTestUtils.setField(game, "id", 1L);

        doReturn(game)
                .when(gameRepository)
                .save(any());

        //when
        Game actual = gameService.createGame();

        // then
        verify(gameRepository).save(any());
        assertThat(actual.getStatus(), equalTo(Status.CREATED));
    }

    @Test
    @DisplayName("Game should be retrieved")
    void gameShouldBeRetrieved() {

        //given
        Game game = new Game();
        ReflectionTestUtils.setField(game, "id", 1L);

        doReturn(Optional.of(game))
                .when(gameRepository)
                .findById(any());

        //when
        Game actual = gameService.getGame(1L);

        // then
        verify(gameRepository).findById(1L);
        assertThat(actual.getId(), equalTo(1L));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when game is not found")
    void shouldThrowExceptionWhenGameNotFound() {

        //given
        doReturn(Optional.empty())
                .when(gameRepository)
                .findById(1L);

        //when, then
        assertThrows(ResourceNotFoundException.class,
                () -> gameService.getGame(1L)
        );
    }

    @Test
    @DisplayName("Should move by pit id")
    void shouldMoveByPitId() {

        //given
        Game game = new Game();
        ReflectionTestUtils.setField(game, "id", 1L);

        doReturn(Optional.of(game))
                .when(gameRepository)
                .findById(any());

        //when
        Game actual = gameService.move(1L, 1);

        // then
        verify(gameRepository).findById(1L);
        verify(gameActionExecutor).execute(game, 1);
        verify(gameRepository).save(game);
        assertThat(actual.getId(), equalTo(1L));
    }
}