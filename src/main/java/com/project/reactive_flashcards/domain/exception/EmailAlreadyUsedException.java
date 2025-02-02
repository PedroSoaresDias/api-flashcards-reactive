package com.project.reactive_flashcards.domain.exception;

public class EmailAlreadyUsedException extends ReactiveFlashcardsException {
    public EmailAlreadyUsedException(final String message) {
        super(message);
    }
}
