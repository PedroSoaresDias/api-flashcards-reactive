package com.project.reactive_flashcards.api.exceptionhandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reactive_flashcards.domain.exception.EmailAlreadyUsedException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EmailAlreadyUsedHandler extends AbstractHandlerException<EmailAlreadyUsedException> {
    public EmailAlreadyUsedHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, EmailAlreadyUsedException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, BAD_REQUEST);
            return ex.getMessage();
          })
              .map(message -> buildError(BAD_REQUEST, message))
              .doFirst(() -> log.error("==== EmailAlreadyUsedException: ", ex))
              .flatMap(response -> writeResponse(exchange, response));
    }

}
