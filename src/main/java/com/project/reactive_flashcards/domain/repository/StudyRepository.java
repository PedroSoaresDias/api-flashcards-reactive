package com.project.reactive_flashcards.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.project.reactive_flashcards.domain.document.StudyDocument;

import reactor.core.publisher.Mono;

@Repository
public interface StudyRepository extends ReactiveMongoRepository<StudyDocument, String> {
    Mono<StudyDocument> findByUserIdAndCompleteFalseAndStudyDeck_DeckId(final String userId, final String deckId);
}
