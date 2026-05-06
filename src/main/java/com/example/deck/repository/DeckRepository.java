package com.example.deck.repository;

import com.example.deck.model.Deck;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeckRepository extends JpaRepository<Deck, UUID> {
    List<Deck> findAllByUserId(String userId, Sort sort);
    Optional<Deck> findByIdAndUserId(UUID id, String userId);
    boolean existsByIdAndUserId(UUID id, String userId);
}
