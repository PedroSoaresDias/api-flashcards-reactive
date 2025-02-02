package com.project.reactive_flashcards.api.exceptionhandler;

import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reactive_flashcards.domain.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class NotFoundHandler extends AbstractHandlerException<NotFoundException> {
    public NotFoundHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, NotFoundException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, NOT_FOUND);
            return ex.getMessage();
          })
              .map(message -> buildError(NOT_FOUND, message))
              .doFirst(() -> log.error("==== NotFoundException: ", ex))
              .flatMap(response -> writeResponse(exchange, response));
    }

}
