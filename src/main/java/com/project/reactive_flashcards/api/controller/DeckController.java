package com.project.reactive_flashcards.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.reactive_flashcards.api.controller.request.DeckRequest;
import com.project.reactive_flashcards.api.controller.response.DeckResponse;
import com.project.reactive_flashcards.api.mapper.DeckMapper;
import com.project.reactive_flashcards.core.validation.MongoId;
import com.project.reactive_flashcards.domain.service.DeckService;
import com.project.reactive_flashcards.domain.service.query.DeckQueryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("decks")
@Slf4j
@AllArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final DeckQueryService deckQueryService;
    private final DeckMapper deckMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<DeckResponse> save(@Valid @RequestBody final DeckRequest request) {
        return deckService.save(deckMapper.toDocument(request))
                .doFirst(() -> log.info("==== Saving a deck with follow data {}", request))
                .map(deckMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<DeckResponse> findById(@PathVariable @Valid @MongoId(message = "{deckController.id}") final String id) {
        return deckQueryService.findById(id)
                .doFirst(() -> log.info("==== Finding a deck with follow id {}", id))
                .map(deckMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Flux<DeckResponse> findAll() {
        return deckQueryService.findAll()
                .doFirst(() -> log.info("==== Finding all decks"))
                .map(deckMapper::toResponse);
    }
}
