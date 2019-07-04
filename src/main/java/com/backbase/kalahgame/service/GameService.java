package com.backbase.kalahgame.service;

import com.backbase.kalahgame.exception.ResourceNotFoundException;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.model.dto.GameResponse;
import com.backbase.kalahgame.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public GameResponse createGame() {

        Game game = gameRepository.save(new Game());

        return GameResponse.builder()
                .id(game.getId().toString())
                .uri(getUri(game))
                .build();
    }

    public GameResponse getGame(Long id) {

        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game is not found with id + " + id));

        return GameResponse.builder()
                .id(game.getId().toString())
                .uri(getUri(game))
                .status(game.getBoard().getStatus())
                .build();
    }

    private URI getUri(Game game) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/games/{id}")
                .buildAndExpand(game.getId())
                .toUri();
    }

}
