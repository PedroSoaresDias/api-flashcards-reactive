package com.project.reactive_flashcards.api.exceptionhandler;

import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reactive_flashcards.domain.exception.DeckInStudyException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DeckInStudyHandler extends AbstractHandlerException<DeckInStudyException> {
    public DeckInStudyHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, DeckInStudyException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, CONFLICT);
            return ex.getMessage();
          })
              .map(message -> buildError(CONFLICT, message))
              .doFirst(() -> log.error("==== DeckInStudyException: ", ex))
              .flatMap(response -> writeResponse(exchange, response));
    }

}
