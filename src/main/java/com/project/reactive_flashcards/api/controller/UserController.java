package com.project.reactive_flashcards.api.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.reactive_flashcards.api.controller.request.UserRequest;
import com.project.reactive_flashcards.api.controller.response.UserResponse;
import com.project.reactive_flashcards.api.mapper.UserMapper;
import com.project.reactive_flashcards.core.validation.MongoId;
import com.project.reactive_flashcards.domain.service.UserService;
import com.project.reactive_flashcards.domain.service.query.UserQueryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;;

@Validated
@RestController
@RequestMapping("users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserQueryService userQueryService;
    private final UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<UserResponse> save(@Valid @RequestBody final UserRequest request) {
        return userService.save(userMapper.toDocument(request))
                .doFirst(() -> log.info("==== Saving a user with follow data {}", request))
                .map(userMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<UserResponse> findById(@PathVariable @Valid @MongoId(message = "{userController.id}") final String id) {
        return userQueryService.findById(id)
                .doFirst(() -> log.info("==== Finding a user with follow id {}", id))
                .map(userMapper::toResponse);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<UserResponse> update(@PathVariable @Valid @MongoId(message = "{userController.id}") final String id,
            @Valid @RequestBody final UserRequest request) {
        return userService.update(userMapper.toDocument(request, id))
                .doFirst(() -> log.info("==== Updating a user with follow info [body: {}, id: {}]", request, id))
                .map(userMapper::toResponse);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable @Valid @MongoId(message = "{userController.id}") final String id) {
        return userService.delete(id)
                .doFirst(() -> log.info("==== Deleting a user with follow id {}", id));
    }
}
