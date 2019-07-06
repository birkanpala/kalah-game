package com.backbase.kalahgame.controller;

import com.backbase.kalahgame.model.dto.GameResponse;
import com.backbase.kalahgame.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame() {

        final GameResponse gameResponse = gameService.createGame();

        return created(URI.create(gameResponse.getUri()))
                .body(gameResponse);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameResponse> getGame(@PathVariable Long gameId) {

        final GameResponse gameResponse = gameService.getGame(gameId);

        return ok().body(gameResponse);
    }

    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<GameResponse> move(@PathVariable Long gameId, @PathVariable int pitId) {

        final GameResponse gameResponse = gameService.move(gameId, pitId);

        return ok().body(gameResponse);
    }

}
