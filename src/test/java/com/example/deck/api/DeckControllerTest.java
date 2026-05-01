package com.example.deck.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import com.example.deck.model.Deck;
import com.example.deck.model.CardRef;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class DeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnOkForListDecks() throws Exception {
        mockMvc.perform(get("/api/decks"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAllowCorsPreflight() throws Exception {
        mockMvc.perform(options("/api/decks")
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"));
    }

    @Test
    public void shouldPersistAndReturnImageUris() throws Exception {
        // 1. Create a deck
        String createDeckJson = objectMapper.writeValueAsString(new CreateDeckRequest("Test Deck", "Standard", "Description"));
        String response = mockMvc.perform(post("/api/decks")
                        .contentType("application/json")
                        .content(createDeckJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Deck deck = objectMapper.readValue(response, Deck.class);
        UUID deckId = deck.getId();

        // 2. Upsert a card with a long imageUri (Scryfall URIs can be > 255 chars)
        String longImageUri = "https://cards.scryfall.io/normal/front/0/c/0ccb851e-99c9-4f25-a1d7-f4757303f119.jpg?156221d60b&very_long_parameter_to_ensure_it_exceeds_two_hundred_and_fifty_five_characters_limit_if_necessary_for_testing_purposes_1234567890";
        CardRef card = new CardRef(
                "oracle-id-1",
                "Test Card",
                "SET",
                "1",
                "{R}",
                "Creature",
                longImageUri
        );
        UpsertDeckCardRequest upsertRequest = new UpsertDeckCardRequest(card, 4, false);
        String upsertJson = objectMapper.writeValueAsString(upsertRequest);

        mockMvc.perform(put("/api/decks/" + deckId + "/cards")
                        .contentType("application/json")
                        .content(upsertJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mainboard[0].card.imageUris", is(longImageUri)));

        // 3. Get the deck again to verify persistence
        mockMvc.perform(get("/api/decks/" + deckId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mainboard[0].card.imageUris", is(longImageUri)));
    }
}
