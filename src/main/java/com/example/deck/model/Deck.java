package com.example.deck.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "decks")
public class Deck {
    @Id
    private UUID id;
    private String name;
    private String format;
    private String description;
    @ElementCollection
    private List<DeckCardEntry> mainboard;
    @ElementCollection
    private List<DeckCardEntry> sideboard;
    private Instant createdAt;
    private Instant updatedAt;

    public Deck() {
    }

    public Deck(UUID id, String name, String format, String description, List<DeckCardEntry> mainboard, List<DeckCardEntry> sideboard, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.description = description;
        this.mainboard = mainboard == null ? new ArrayList<>() : new ArrayList<>(mainboard);
        this.sideboard = sideboard == null ? new ArrayList<>() : new ArrayList<>(sideboard);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DeckCardEntry> getMainboard() {
        return mainboard;
    }

    public void setMainboard(List<DeckCardEntry> mainboard) {
        this.mainboard = mainboard;
    }

    public List<DeckCardEntry> getSideboard() {
        return sideboard;
    }

    public void setSideboard(List<DeckCardEntry> sideboard) {
        this.sideboard = sideboard;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}