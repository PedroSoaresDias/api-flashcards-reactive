package com.project.reactive_flashcards.domain.service.query;

import org.springframework.stereotype.Service;

import com.project.reactive_flashcards.domain.document.StudyDocument;
import com.project.reactive_flashcards.domain.exception.NotFoundException;
import com.project.reactive_flashcards.domain.repository.StudyRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.STUDY_NOT_FOUND;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class StudyQueryService {
    private final StudyRepository studyRepository;

    public Mono<StudyDocument> findByPendingStudyByUserIdAndDeckId(final String userId, final String deckId) {
        return studyRepository.findByUserIdAndCompleteFalseAndStudyDeck_DeckId(userId, deckId)
        .doFirst(() -> log.info("==== Try to get pending study with userId {} and deckId {}", userId, deckId))
        .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(
                        () -> 
                        Mono.error(new NotFoundException(STUDY_NOT_FOUND.params(userId, deckId).getMessage()))));
    }
}
