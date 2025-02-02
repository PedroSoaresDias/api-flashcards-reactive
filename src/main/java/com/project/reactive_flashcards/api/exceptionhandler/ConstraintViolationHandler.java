package com.project.reactive_flashcards.api.exceptionhandler;

import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reactive_flashcards.api.controller.response.ErrorFieldResponse;
import com.project.reactive_flashcards.api.controller.response.ProblemResponse;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.hibernate.validator.internal.engine.path.PathImpl;

@Slf4j
public class ConstraintViolationHandler extends AbstractHandlerException<ConstraintViolationException> {
    public ConstraintViolationHandler(final ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, ConstraintViolationException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, BAD_REQUEST);
            return GENERIC_BAD_REQUEST.getMessage();
        })
                .map(message -> buildError(BAD_REQUEST, message))
                .flatMap(response -> buildRequestErrorMessage(response, ex))
                .doFirst(() -> log.error("==== ConstraintViolationException: ", ex))
                .flatMap(response -> writeResponse(exchange, response));
    }
    
    private Mono<ProblemResponse> buildRequestErrorMessage(final ProblemResponse response,
      final ConstraintViolationException ex) {
    return Flux.fromIterable(ex.getConstraintViolations())
        .map(constraintViolation -> ErrorFieldResponse.builder()
            .name(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString())
            .message(constraintViolation.getMessage()).build())
        .collectList()
        .map(problems -> response.toBuilder().fields(problems).build());
  }
}
