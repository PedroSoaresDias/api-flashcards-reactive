package com.project.reactive_flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.reactive_flashcards.core.validation.MongoId;

import lombok.Builder;

public record StudyRequest(@MongoId @JsonProperty("userId") String userId, @MongoId @JsonProperty("deckId") String deckId) {
    @Builder(toBuilder = true)
    public StudyRequest{}
}
