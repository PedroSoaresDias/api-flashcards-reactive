package com.project.reactive_flashcards.domain.DTO;

import java.util.Set;

import lombok.Builder;

public record StudyDeckDTO(String deckId, Set<StudyCardDTO> cards) {
    @Builder(toBuilder = true)
    public StudyDeckDTO{}
}
