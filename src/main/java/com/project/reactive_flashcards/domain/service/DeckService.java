package com.project.reactive_flashcards.domain.service;

import org.springframework.stereotype.Service;

import com.project.reactive_flashcards.domain.document.DeckDocument;
import com.project.reactive_flashcards.domain.repository.DeckRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class DeckService {
    private final DeckRepository deckRepository;

    public Mono<DeckDocument> save(final DeckDocument document) {
        return deckRepository.save(document)
                .doFirst(() -> log.info("==== Try to save a follow deck {}", document));
    }
}
