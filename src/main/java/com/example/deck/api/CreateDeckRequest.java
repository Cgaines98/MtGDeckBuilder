package com.example.deck.api;

import jakarta.validation.constraints.NotBlank;

public record CreateDeckRequest(
        @NotBlank String name,
        @NotBlank String format,
        String description
) {
}