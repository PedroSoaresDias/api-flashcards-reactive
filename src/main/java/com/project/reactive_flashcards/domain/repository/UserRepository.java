package com.project.reactive_flashcards.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.project.reactive_flashcards.domain.document.UserDocument;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserDocument, String>{
    Mono<UserDocument> findByEmail(final String email);
}
