package com.project.reactive_flashcards.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.reactive_flashcards.api.controller.request.DeckRequest;
import com.project.reactive_flashcards.api.controller.response.DeckResponse;
import com.project.reactive_flashcards.domain.document.DeckDocument;

@Mapper(componentModel = "spring")
public interface DeckMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest request, final String id);

    DeckResponse toResponse(final DeckDocument document);
}
