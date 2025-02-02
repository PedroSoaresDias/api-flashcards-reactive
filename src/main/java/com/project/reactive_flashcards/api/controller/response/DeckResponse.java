package com.project.reactive_flashcards.api.controller.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

public record DeckResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")  
        String name,
        @JsonProperty("description")    
        String description,
        @JsonProperty("cards")
        Set<CardResponse> cards) {

        @Builder(toBuilder = true)
        public DeckResponse{}
}
