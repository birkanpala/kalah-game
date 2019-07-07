package com.backbase.kalahgame.controller;

import com.backbase.kalahgame.controller.dto.GameResponse;
import com.backbase.kalahgame.exception.ResourceNotFoundException;
import com.backbase.kalahgame.model.Game;
import com.backbase.kalahgame.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    @DisplayName("POST /games - 201_Created")
    void shouldCreateGame() throws Exception {

        //given
        Game game = new Game();
        ReflectionTestUtils.setField(game, "id", 1L);

        GameResponse response = GameResponse.builder()
                .id("1")
                .uri("http://localhost/games/1")
                .build();

        doReturn(game).when(gameService).createGame();

        //when
        mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(header().string(HttpHeaders.LOCATION, response.getUri()))

                .andExpect(jsonPath("$.id", equalTo(response.getId())))
                .andExpect(jsonPath("$.uri", equalTo(response.getUri())));
    }

    @Test
    @DisplayName("GET /games/{gameId} - 200_OK")
    void shouldGetGame() throws Exception {

        //given
        Game game = new Game();
        ReflectionTestUtils.setField(game, "id", 1L);

        GameResponse response = GameResponse.builder()
                .id("1")
                .uri("http://localhost/games/1")
                .status(game.getBoard().getStatus())
                .build();

        doReturn(game).when(gameService).getGame(1L);

        //when
        mockMvc.perform(get("/games/{gameId}", 1))

                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                .andExpect(jsonPath("$.id", equalTo(response.getId())))
                .andExpect(jsonPath("$.uri", equalTo(response.getUri())))
                .andExpect(jsonPath("$.status", equalTo(response.getStatus())));
    }

    @Test
    @DisplayName("GET /games/{gameId} - 404_Not_Found")
    void shouldReturnStatusNotFoundWhenGameNotExist() throws Exception {

        //given
        doThrow(ResourceNotFoundException.class)
                .when(gameService).getGame(1L);

        //when
        mockMvc.perform(get("/games/{gameId}", 1))

                //then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /games/{gameId}/pits - 200_OK")
    void shouldPlayGame() throws Exception {

        //given
        Game game = new Game();
        ReflectionTestUtils.setField(game, "id", 1L);

        GameResponse response = GameResponse.builder()
                .id("1")
                .uri("http://localhost/games/1")
                .status(game.getBoard().getStatus())
                .build();

        doReturn(game).when(gameService).move(1L, 1);

        //when
        mockMvc.perform(put("/games/{gameId}/pits/{pitId}", 1, 1))

                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                .andExpect(jsonPath("$.id", equalTo(response.getId())))
                .andExpect(jsonPath("$.uri", equalTo(response.getUri())))
                .andExpect(jsonPath("$.status", equalTo(response.getStatus())));
    }

    @Test
    @DisplayName("PUT /games/{gameId}/pits - 400_Bad_Request - Invalid move")
    void shouldReturnStatusBadRequestWhenInvalidMove() throws Exception {

        //given
        doThrow(IllegalArgumentException.class)
                .when(gameService).move(1L, 1);

        //when
        mockMvc.perform(put("/games/{gameId}/pits/{pitId}", 1, 1))

                //then
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /games/{gameId}/pits - 400_Bad_Request - Invalid State")
    void shouldReturnStatusBadRequestWhenInvalidState() throws Exception {

        //given
        doThrow(IllegalStateException.class)
                .when(gameService).move(1L, 1);

        //when
        mockMvc.perform(put("/games/{gameId}/pits/{pitId}", 1, 1))

                //then
                .andExpect(status().isBadRequest());
    }

}