package com.project.reactive_flashcards.domain.service.query;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.DECK_NOT_FOUND;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.project.reactive_flashcards.domain.repository.DeckRepository;
import com.project.reactive_flashcards.domain.document.DeckDocument;
import com.project.reactive_flashcards.domain.exception.NotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class DeckQueryService {
    private final DeckRepository deckRepository;

    public Mono<DeckDocument> findById(final String id) {
        return deckRepository.findById(id)
                .doFirst(() -> log.info("==== Try to find deck with id {}", id))
                .filter(Objects::nonNull)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(new NotFoundException(DECK_NOT_FOUND.params(id).getMessage()))));
    }

    public Flux<DeckDocument> findAll() {
        return deckRepository.findAll()
                .doFirst(() -> log.info("==== Try to get all decks"));
    }
}
