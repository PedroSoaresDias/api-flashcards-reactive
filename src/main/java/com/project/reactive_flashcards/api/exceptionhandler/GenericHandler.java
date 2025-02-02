package com.project.reactive_flashcards.api.exceptionhandler;

import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@Slf4j
public class GenericHandler extends AbstractHandlerException<Exception> {
    public GenericHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, Exception ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, INTERNAL_SERVER_ERROR);
            return GENERIC_EXCEPTION.getMessage();
          })
              .map(message -> buildError(INTERNAL_SERVER_ERROR, message))
              .doFirst(() -> log.error("==== Exception: ", ex))
              .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

}
