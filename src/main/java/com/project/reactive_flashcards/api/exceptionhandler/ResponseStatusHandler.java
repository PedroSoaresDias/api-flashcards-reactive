package com.project.reactive_flashcards.api.exceptionhandler;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class ResponseStatusHandler extends AbstractHandlerException<ResponseStatusException> {
    public ResponseStatusHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, ResponseStatusException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, NOT_FOUND);
            return GENERIC_NOT_FOUND.getMessage();
          })
              .map(message -> buildError(NOT_FOUND, message))
              .doFirst(() -> log.error("==== ResponseStatusException: ", ex))
              .flatMap(response -> writeResponse(exchange, response));
    }
}
