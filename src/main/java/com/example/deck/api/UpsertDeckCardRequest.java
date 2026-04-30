package com.example.deck.api;

import com.example.deck.model.CardRef;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpsertDeckCardRequest(
        @NotNull @Valid CardRef card,
        @Min(1) int quantity,
        boolean sideboard
) {
}