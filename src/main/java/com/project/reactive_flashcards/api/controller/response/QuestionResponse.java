package com.project.reactive_flashcards.api.controller.response;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

public record QuestionResponse(@JsonProperty("asked") String asked, @JsonProperty("askedIn") OffsetDateTime askedIn) {
    @Builder(toBuilder = true)
    public QuestionResponse{}
}
