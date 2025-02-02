package com.project.reactive_flashcards.api.exceptionhandler;

import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.OffsetDateTime;

import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reactive_flashcards.api.controller.response.ProblemResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractHandlerException<T extends Exception> {
    private final ObjectMapper mapper;

    abstract Mono<Void> handlerException(final ServerWebExchange exchange, final T ex);
    
    protected Mono<Void> writeResponse(final ServerWebExchange exchange, final ProblemResponse problemResponse) {
        return exchange.getResponse()
                .writeWith(Mono.fromCallable(
                        () -> new DefaultDataBufferFactory().wrap(mapper.writeValueAsBytes(problemResponse))));
    }

    protected void prepareExchange(final ServerWebExchange exchange, final HttpStatus statusCode) {
        exchange.getResponse().setStatusCode(statusCode);
        exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
    }

    protected ProblemResponse buildError(final HttpStatus status, final String errorDescription) {
        return ProblemResponse.builder()
                .status(status.value())
                .errorDescription(errorDescription)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
