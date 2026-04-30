package com.example.deck.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record DeckCardEntry(
        @NotNull @Valid @Embedded CardRef card,
        @Min(1) int quantity
) {
}