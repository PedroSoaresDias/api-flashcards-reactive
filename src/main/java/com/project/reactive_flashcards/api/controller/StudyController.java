package com.project.reactive_flashcards.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.reactive_flashcards.api.controller.request.StudyRequest;
import com.project.reactive_flashcards.api.controller.response.QuestionResponse;
import com.project.reactive_flashcards.api.mapper.StudyMapper;
import com.project.reactive_flashcards.domain.service.StudyService;

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
    private final StudyMapper studyMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody final StudyRequest request) {
        return studyService.start(studyMapper.toDocument(request))
        .doFirst(() -> log.info("==== Try to create a study with follow request {}", request))
                .map(document -> studyMapper.toResponse(document.getLastQuestionPending()));
    }
}
