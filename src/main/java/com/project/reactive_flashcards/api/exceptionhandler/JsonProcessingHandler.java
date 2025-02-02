package com.project.reactive_flashcards.api.exceptionhandler;

import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_METHOD_NOT_ALLOW;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@Slf4j
public class JsonProcessingHandler extends AbstractHandlerException<JsonProcessingException> {
    public JsonProcessingHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, JsonProcessingException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, METHOD_NOT_ALLOWED);
            return GENERIC_METHOD_NOT_ALLOW.getMessage();
          })
              .map(message -> buildError(METHOD_NOT_ALLOWED, message))
              .doFirst(() -> log.error("==== JsonProcessingException: Failed to map exception for the request {}",
                  exchange.getRequest().getPath().value(), ex))
              .flatMap(response -> writeResponse(exchange, response));
    }

}
