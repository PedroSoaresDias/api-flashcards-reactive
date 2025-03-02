package com.project.reactive_flashcards.domain.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang3.ArrayUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseErrorMessage {
    private final String DEFAULT_RECOURSE = "messages";

    public static final BaseErrorMessage GENERIC_EXCEPTION = new BaseErrorMessage("generic");
    public static final BaseErrorMessage GENERIC_NOT_FOUND = new BaseErrorMessage("generic.notFound");
    public static final BaseErrorMessage GENERIC_METHOD_NOT_ALLOW = new BaseErrorMessage("generic.methodNotAllow");
    public static final BaseErrorMessage GENERIC_BAD_REQUEST = new BaseErrorMessage("generic.badRequest");
    public static final BaseErrorMessage USER_NOT_FOUND = new BaseErrorMessage("user.notFound");
    public static final BaseErrorMessage DECK_NOT_FOUND = new BaseErrorMessage("deck.notFound");
    public static final BaseErrorMessage EMAIL_ALREADY_USED = new BaseErrorMessage("user.emailAlreadyUsed");
    public static final BaseErrorMessage STUDY_DECK_NOT_FOUND = new BaseErrorMessage("studyDeck.notFound");
    public static final BaseErrorMessage DECK_IN_STUDY = new BaseErrorMessage("study.DeckInStudy");
    public static final BaseErrorMessage STUDY_NOT_FOUND = new BaseErrorMessage("study.notFound");
    public static final BaseErrorMessage STUDY_QUESTION_NOT_FOUND = new BaseErrorMessage("studyQuestion.notFound");

    private final String key;

    private String[] params;

    public BaseErrorMessage params(final String... params) {
        this.params = ArrayUtils.clone(params);
        return this;
    }

    public String getMessage() {
        var message = tryGetMessageFromBundle();
        if (ArrayUtils.isNotEmpty(params)) {
            final var fmt = new MessageFormat(message);
            message = fmt.format(params);
        }

        return message;
    }

    private String tryGetMessageFromBundle() {
        return getResource().getString(key);
    }

    public ResourceBundle getResource() {
        return ResourceBundle.getBundle(DEFAULT_RECOURSE);
    }
}
