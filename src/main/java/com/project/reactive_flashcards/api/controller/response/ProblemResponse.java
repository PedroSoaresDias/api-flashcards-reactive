package com.project.reactive_flashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(NON_NULL)
public record ProblemResponse(Integer status,
        OffsetDateTime timestamp,
        String errorDescription,
        List<ErrorFieldResponse> fields) {

    @Builder(toBuilder = true)
    public ProblemResponse {
    }

}
