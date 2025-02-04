package com.project.reactive_flashcards.api.controller.response;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

public record AnswerQuestionResponse(
    @JsonProperty("asked") String asked,
    @JsonProperty("askedIn") OffsetDateTime askedIn,
    @JsonProperty("answered") String answered,
    @JsonProperty("answeredIn") OffsetDateTime answeredIn,
    @JsonProperty("expected") String expected) {

  @Builder(toBuilder = true)
  public AnswerQuestionResponse {}
}
