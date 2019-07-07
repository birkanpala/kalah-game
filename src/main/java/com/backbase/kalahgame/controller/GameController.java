package com.backbase.kalahgame.controller;

import com.backbase.kalahgame.controller.dto.GameResponse;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame() {

        final Game game = gameService.createGame();
        final GameResponse gameResponse = createGameResponse(game);

        return created(URI.create(gameResponse.getUri()))
                .body(gameResponse);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameResponse> getGame(@PathVariable Long gameId) {

        final Game game = gameService.getGame(gameId);

        return ok()
                .body(createGameResponseWithStatus(game));
    }

    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<GameResponse> move(@PathVariable Long gameId, @PathVariable int pitId) {

        final Game game = gameService.move(gameId, pitId);

        return ok()
                .body(createGameResponseWithStatus(game));
    }

    private GameResponse createGameResponse(final Game game) {
        return GameResponse.builder()
                .id(game.getId().toString())
                .uri(getUri(game.getId()))
                .build();
    }

    private GameResponse createGameResponseWithStatus(final Game game) {
        return GameResponse.builder()
                .id(game.getId().toString())
                .uri(getUri(game.getId()))
                .status(game.getBoard().getStatus())
                .build();
    }

    private String getUri(final long id) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/games/{id}")
                .buildAndExpand(id)
                .toUri()
                .toASCIIString();
    }
}
