package com.project.reactive_flashcards.domain.service.query;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import com.project.reactive_flashcards.domain.document.Question;
import com.project.reactive_flashcards.domain.document.StudyDocument;
import com.project.reactive_flashcards.domain.exception.NotFoundException;
import com.project.reactive_flashcards.domain.repository.StudyRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.STUDY_NOT_FOUND;
import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.STUDY_QUESTION_NOT_FOUND;
import static com.project.reactive_flashcards.domain.exception.BaseErrorMessage.STUDY_DECK_NOT_FOUND;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class StudyQueryService {
  private final StudyRepository studyRepository;

  public Mono<StudyDocument> findByPendingStudyByUserIdAndDeckId(final String userId, final String deckId) {
    return studyRepository.findByUserIdAndCompleteFalseAndStudyDeck_DeckId(userId, deckId)
        .doFirst(() -> log.info("==== Try to get pending study with userId {} and deckId {}",
            userId, deckId))
        .filter(Objects::nonNull)
        .switchIfEmpty(Mono.defer(
            () -> Mono.error(
                new NotFoundException(STUDY_DECK_NOT_FOUND
                    .params(userId, deckId)
                    .getMessage()))));
  }

  public Mono<StudyDocument> findById(final String id) {
    return studyRepository.findById(id)
        .doFirst(() -> log.info("==== Getting a study with id {}", id))
        .filter(Objects::nonNull)
        .switchIfEmpty(
            Mono.defer(() -> Mono.error(new NotFoundException(
                STUDY_NOT_FOUND.params(id).getMessage()))));
  }

  public Mono<StudyDocument> verifyIfFinished(final StudyDocument document) {
      return Mono.just(document)
    .doFirst(() -> log.info("==== Verify if study has some question without right answer"))
        .filter(study -> BooleanUtils.isFalse(document.complete()))
        .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(STUDY_QUESTION_NOT_FOUND.params(document.id()).getMessage()))));
  }

  public Mono<Question> getLastPendingQuestion(final String id) {
    return findById(id)
        .flatMap(this::verifyIfFinished)
        .flatMapMany(study -> Flux.fromIterable(study.questions()))
        .filter(Question::isNotAnswered)
        .doFirst(() -> log.info("==== Getting a current pending question in study {}", id))
        .single();
  }
}
