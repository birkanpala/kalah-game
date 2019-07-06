package com.backbase.kalahgame.service;

import com.backbase.kalahgame.exception.ResourceNotFoundException;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.dto.GameResponse;
import com.backbase.kalahgame.repository.GameRepository;
import com.backbase.kalahgame.service.chain.GameActionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final GameRepository gameRepository;

    private final GameActionExecutor gameActionExecutor;

    /**
     * Creates a new game.
     *
     * @return {@code GameResponse} DTO
     */
    public GameResponse createGame() {

        final Game game = gameRepository.save(new Game());

        log.info("Game [{}] is created!", game.getId());

        return createGameResponse(game);
    }

    /**
     * Retrieves the game from repository.
     *
     * @param id game Id
     * @return {@code GameResponse} DTO
     */
    public GameResponse getGame(final Long id) {

        final Game game = findGame(id);

        log.debug("Game [{}] is loaded!", game.getId());

        return createGameResponseWithStatus(game);
    }

    /**
     * Plays the game for the given pit.
     *
     * @param id    game Id
     * @param pitId pit Id
     * @return {@code GameResponse} DTO
     */
    public GameResponse move(final Long id, final int pitId) {

        final Game game = findGame(id);

        gameActionExecutor.execute(game, pitId);

        gameRepository.save(game);

        log.info("Game [{}] is played!", game.getId());

        return createGameResponseWithStatus(game);
    }

    private Game findGame(final Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game is not found with id " + id));
    }

    private GameResponse createGameResponse(final Game game) {
        return GameResponse.builder()
                .id(game.getId().toString())
                .uri(getUri(game))
                .build();
    }

    private GameResponse createGameResponseWithStatus(final Game game) {
        return GameResponse.builder()
                .id(game.getId().toString())
                .uri(getUri(game))
                .status(game.getBoard().getStatus())
                .build();
    }

    private String getUri(final Game game) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/games/{id}")
                .buildAndExpand(game.getId())
                .toUri()
                .toASCIIString();
    }

}
