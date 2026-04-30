package com.example.deck.service;

import com.example.deck.api.CreateDeckRequest;
import com.example.deck.api.UpdateDeckRequest;
import com.example.deck.api.UpsertDeckCardRequest;
import com.example.deck.model.Deck;
import com.example.deck.model.DeckCardEntry;
import com.example.deck.repository.DeckRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DeckService {

    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public List<Deck> listDecks() {
        return deckRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
    }

    public Deck getDeck(UUID deckId) {
        return deckRepository.findById(deckId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));
    }

    public Deck createDeck(CreateDeckRequest request) {
        Instant now = Instant.now();
        Deck deck = new Deck(
                UUID.randomUUID(),
                request.name().trim(),
                request.format().trim(),
                request.description(),
                new ArrayList<>(),
                new ArrayList<>(),
                now,
                now
        );
        return deckRepository.save(deck);
    }

    public Deck updateDeck(UUID deckId, UpdateDeckRequest request) {
        Deck deck = getDeck(deckId);
        deck.setName(request.name().trim());
        deck.setFormat(request.format().trim());
        deck.setDescription(request.description());
        deck.setUpdatedAt(Instant.now());
        return deckRepository.save(deck);
    }

    public void deleteDeck(UUID deckId) {
        if (!deckRepository.existsById(deckId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found");
        }
        deckRepository.deleteById(deckId);
    }

    public Deck upsertCard(UUID deckId, UpsertDeckCardRequest request) {
        Deck deck = getDeck(deckId);
        List<DeckCardEntry> target = request.sideboard() ? deck.getSideboard() : deck.getMainboard();

        int index = -1;
        for (int i = 0; i < target.size(); i++) {
            DeckCardEntry entry = target.get(i);
            if (entry.card().oracleId().equals(request.card().oracleId())) {
                index = i;
                break;
            }
        }

        DeckCardEntry next = new DeckCardEntry(request.card(), request.quantity());
        if (index >= 0) {
            target.set(index, next);
        } else {
            target.add(next);
        }

        deck.setUpdatedAt(Instant.now());
        return deckRepository.save(deck);
    }

    public Deck removeCard(UUID deckId, String oracleId, boolean sideboard) {
        Deck deck = getDeck(deckId);
        List<DeckCardEntry> target = sideboard ? deck.getSideboard() : deck.getMainboard();
        target.removeIf(entry -> entry.card().oracleId().equals(oracleId));
        deck.setUpdatedAt(Instant.now());
        return deckRepository.save(deck);
    }
}