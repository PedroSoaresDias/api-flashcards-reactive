package com.project.reactive_flashcards.domain.DTO;

import lombok.Builder;

public record StudyCardDTO(String front, String back) {
    @Builder(toBuilder = true)
    public StudyCardDTO {}
}
