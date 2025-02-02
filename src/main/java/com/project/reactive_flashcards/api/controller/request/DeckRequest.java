package com.project.reactive_flashcards.api.controller.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record DeckRequest(
        @NotBlank
        @Size(min = 1, max = 255)
        @JsonProperty("name")    
        String name,
        @NotBlank
        @Size(min = 1, max = 255)
        @JsonProperty("description")    
        String description,
        @Valid
        @Size(min = 3)
        @NotNull
        @JsonProperty("cards")
        Set<CardRequest> cards) {

    @Builder(toBuilder = true)
    public DeckRequest{}
}
