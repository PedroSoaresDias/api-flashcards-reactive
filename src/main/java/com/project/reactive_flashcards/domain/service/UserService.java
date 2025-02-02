package com.project.reactive_flashcards.domain.service;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.EMAIL_ALREADY_USED;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.project.reactive_flashcards.domain.document.UserDocument;
import com.project.reactive_flashcards.domain.exception.EmailAlreadyUsedException;
import com.project.reactive_flashcards.domain.exception.NotFoundException;
import com.project.reactive_flashcards.domain.repository.UserRepository;
import com.project.reactive_flashcards.domain.service.query.UserQueryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    public Mono<UserDocument> save(final UserDocument document) {
        return userQueryService.findByEmail(document.email())
                .filter(Objects::isNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new EmailAlreadyUsedException(EMAIL_ALREADY_USED.params(document.email()).getMessage()))))
                .doFirst(() -> log.info("==== Try to save a follow documento {}", document))
                .onErrorResume(NotFoundException.class, e -> userRepository.save(document));
    }

    public Mono<UserDocument> update(final UserDocument document) {
        return verifyEmail(document)
                .then(userQueryService.findById(document.id())
                        .map(user -> document.toBuilder()
                                .createdAt(user.createdAt())
                                .updatedAt(user.updatedAt())
                                .build())
                        .flatMap(userRepository::save)
                        .doFirst(() -> log.info("==== Try to update user with follow info {}", document)));
    }

    public Mono<Void> delete(final String id) {
        return userQueryService.findById(id)
                .flatMap(userRepository::delete)
                .doFirst(() -> log.info("==== Try to delete a user with follow id {}", id));
    }

    private Mono<Void> verifyEmail(final UserDocument document) {
        return userQueryService.findByEmail(document.email())
                .filter(stored -> stored.id().equals(document.id()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new EmailAlreadyUsedException(EMAIL_ALREADY_USED.params(document.email()).getMessage()))))
                .onErrorResume(NotFoundException.class, e -> Mono.empty())
                .then();
    }
}
