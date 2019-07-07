package com.backbase.kalahgame.integration;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("int-test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class KalahGameIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should create and get game")
    void shouldCreateAndGetGame() throws Exception {

        //create game
        mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", equalTo("1")));


        //get game
        mockMvc.perform(get("/games/{gameId}", 1))

                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                .andExpect(jsonPath("$.id", equalTo("1")))
                .andExpect(jsonPath("$.uri", equalTo("http://localhost/games/1")));
    }

    @Test
    @DisplayName("Should create and play game")
    void shouldCreateAndPlayGame() throws Exception {

        //create game
        mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", equalTo("1")));


        //play game
        mockMvc.perform(put("/games/{gameId}/pits/{pitId}", 1, 1))

                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                .andExpect(jsonPath("$.id", equalTo("1")))
                .andExpect(jsonPath("$.uri", equalTo("http://localhost/games/1")));
    }

}
