package com.project.reactive_flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record AnswerQuestionRequest(
        @JsonProperty("answer") 
        @NotBlank 
        @Size(min = 1, max = 255) 
        String answer) {

    @Builder(toBuilder = true)
    public AnswerQuestionRequest{}
}
