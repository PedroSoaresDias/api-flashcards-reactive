package com.project.reactive_flashcards.domain.service.query;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.project.reactive_flashcards.domain.document.UserDocument;
import com.project.reactive_flashcards.domain.exception.NotFoundException;
import com.project.reactive_flashcards.domain.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;

    public Mono<UserDocument> findById(final String id) {
        return userRepository.findById(id)
                .doFirst(() -> log.info("==== Try to find user with id {}", id))
                .filter(Objects::nonNull)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("id", id).getMessage()))));
    }
    
    public Mono<UserDocument> findByEmail(final String email) {
        return userRepository.findByEmail(email)
                .doFirst(() -> log.info("==== Try to find user with email {}", email))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("email", email).getMessage()))));
    }   
}
