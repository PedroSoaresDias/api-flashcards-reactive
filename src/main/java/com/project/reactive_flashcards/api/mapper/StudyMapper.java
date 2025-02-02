package com.project.reactive_flashcards.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.reactive_flashcards.api.controller.request.StudyRequest;
import com.project.reactive_flashcards.api.controller.response.QuestionResponse;
import com.project.reactive_flashcards.domain.document.Question;
import com.project.reactive_flashcards.domain.document.StudyDocument;

@Mapper(componentModel = "spring")
public interface StudyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studyDeck.deckId", source = "deckId")
    @Mapping(target = "studyDeck.cards", ignore = true)
    @Mapping(target = "complete", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StudyDocument toDocument(final StudyRequest request);

    QuestionResponse toResponse(final Question question);
}
