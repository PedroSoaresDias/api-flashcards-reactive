package com.project.reactive_flashcards.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.reactive_flashcards.api.controller.request.UserRequest;
import com.project.reactive_flashcards.api.controller.response.UserResponse;
import com.project.reactive_flashcards.domain.document.UserDocument;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request, final String id);

    UserResponse toResponse(final UserDocument document);
}
