package com.example.deck.api;

import com.example.deck.model.Deck;
import com.example.deck.service.DeckService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public List<Deck> listDecks(@AuthenticationPrincipal Jwt jwt) {
        return deckService.listDecks(jwt.getSubject());
    }

    @GetMapping("/{deckId}")
    public Deck getDeck(@PathVariable UUID deckId, @AuthenticationPrincipal Jwt jwt) {
        return deckService.getDeck(deckId, jwt.getSubject());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Deck createDeck(@Valid @RequestBody CreateDeckRequest request, @AuthenticationPrincipal Jwt jwt) {
        return deckService.createDeck(request, jwt.getSubject());
    }

    @PutMapping("/{deckId}")
    public Deck updateDeck(@PathVariable UUID deckId, @Valid @RequestBody UpdateDeckRequest request, @AuthenticationPrincipal Jwt jwt) {
        return deckService.updateDeck(deckId, request, jwt.getSubject());
    }

    @DeleteMapping("/{deckId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeck(@PathVariable UUID deckId, @AuthenticationPrincipal Jwt jwt) {
        deckService.deleteDeck(deckId, jwt.getSubject());
    }

    @PutMapping("/{deckId}/cards")
    public Deck upsertCard(@PathVariable UUID deckId, @Valid @RequestBody UpsertDeckCardRequest request, @AuthenticationPrincipal Jwt jwt) {
        return deckService.upsertCard(deckId, request, jwt.getSubject());
    }

    @DeleteMapping("/{deckId}/cards/{oracleId}")
    public Deck removeCard(
            @PathVariable UUID deckId,
            @PathVariable String oracleId,
            @RequestParam(defaultValue = "false") boolean sideboard,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return deckService.removeCard(deckId, oracleId, sideboard, jwt.getSubject());
    }
}