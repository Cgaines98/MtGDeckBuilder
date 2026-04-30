package com.example.deck.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record CardRef(
        @NotBlank String oracleId,
        @NotBlank String name,
        String setCode,
        String collectorNumber
) {
}