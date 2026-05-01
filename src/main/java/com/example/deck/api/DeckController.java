package com.example.deck.api;

import com.example.deck.model.Deck;
import com.example.deck.service.DeckService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public List<Deck> listDecks() {
        return deckService.listDecks();
    }

    @GetMapping("/{deckId}")
    public Deck getDeck(@PathVariable UUID deckId) {
        return deckService.getDeck(deckId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Deck createDeck(@Valid @RequestBody CreateDeckRequest request) {
        return deckService.createDeck(request);
    }

    @PutMapping("/{deckId}")
    public Deck updateDeck(@PathVariable UUID deckId, @Valid @RequestBody UpdateDeckRequest request) {
        return deckService.updateDeck(deckId, request);
    }

    @DeleteMapping("/{deckId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeck(@PathVariable UUID deckId) {
        deckService.deleteDeck(deckId);
    }

    @PutMapping("/{deckId}/cards")
    public Deck upsertCard(@PathVariable UUID deckId, @Valid @RequestBody UpsertDeckCardRequest request) {
        return deckService.upsertCard(deckId, request);
    }

    @DeleteMapping("/{deckId}/cards/{oracleId}")
    public Deck removeCard(
            @PathVariable UUID deckId,
            @PathVariable String oracleId,
            @RequestParam(defaultValue = "false") boolean sideboard
    ) {
        return deckService.removeCard(deckId, oracleId, sideboard);
    }
}