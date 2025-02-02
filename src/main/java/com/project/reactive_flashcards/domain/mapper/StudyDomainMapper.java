package com.project.reactive_flashcards.domain.mapper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.reactive_flashcards.domain.document.Card;
import com.project.reactive_flashcards.domain.document.Question;
import com.project.reactive_flashcards.domain.document.StudyCard;

@Mapper(componentModel = "spring")
public interface StudyDomainMapper {
    StudyCard toStudyCard(final Card cards);

    default Question generateRandomQuestion(final Set<StudyCard> cards) {
        var values = new ArrayList<>(cards);
        var random = new Random();
        var position = random.nextInt(values.size());
        return toQuestion(values.get(position));
    }

    @Mapping(target = "asked", source = "front")
    @Mapping(target = "askedIn", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "answered", ignore = true)
    @Mapping(target = "answeredIn", ignore = true)
    @Mapping(target = "expected", source = "back")
    Question toQuestion(final StudyCard card);
}
