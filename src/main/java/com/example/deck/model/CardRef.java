package com.example.deck.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record CardRef(
        @NotBlank String oracleId,
        @NotBlank String name,
        String setCode,
        String collectorNumber,
        String manaCost,
        String typeLine,
        @Column(columnDefinition = "TEXT") String imageUris
) {
}