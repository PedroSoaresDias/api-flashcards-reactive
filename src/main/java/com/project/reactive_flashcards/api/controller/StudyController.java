package com.project.reactive_flashcards.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.reactive_flashcards.api.controller.request.AnswerQuestionRequest;
import com.project.reactive_flashcards.api.controller.request.StudyRequest;
import com.project.reactive_flashcards.api.controller.response.AnswerQuestionResponse;
import com.project.reactive_flashcards.api.controller.response.QuestionResponse;
import com.project.reactive_flashcards.api.mapper.StudyMapper;
import com.project.reactive_flashcards.core.validation.MongoId;
import com.project.reactive_flashcards.domain.service.StudyService;
import com.project.reactive_flashcards.domain.service.query.StudyQueryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("studies")
@Slf4j
@AllArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final StudyQueryService studyQueryService;
    private final StudyMapper studyMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody final StudyRequest request) {
        return studyService.start(studyMapper.toDocument(request))
                .doFirst(() -> log.info("==== Try to create a study with follow request {}", request))
                .map(document -> studyMapper.toResponse(document.getLastPendingQuestion(), document.id()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}/current-question")
    public Mono<QuestionResponse> getCurrentQuestion(
            @Valid @PathVariable @MongoId(message = "{studyController.id}") final String id) {
        return studyQueryService.getLastPendingQuestion(id)
                .doFirst(() -> log.info("==== Try to get a next question in study {}", id))
                .map(question -> studyMapper.toResponse(question, id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "{id}/answer")
    public Mono<AnswerQuestionResponse> answer(
            @Valid @PathVariable @MongoId(message = "{studyController.id}") final String id,
            @Valid @RequestBody final AnswerQuestionRequest resquest) {
        return studyService.answer(id, resquest.answer())
                .doFirst(() -> log.info("==== Try to answer pending question in study {} with {}", id,
                        resquest.answer()))
                .map(document -> studyMapper.toResponse(document.getLastAnsweredQuestion()));
    }

}
