package com.project.reactive_flashcards.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.project.reactive_flashcards.domain.document.DeckDocument;

@Repository
public interface DeckRepository extends ReactiveMongoRepository<DeckDocument, String> {

}
