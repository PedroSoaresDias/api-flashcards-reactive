package com.project.reactive_flashcards.domain.document;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Field;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record Question(String asked, @Field("asked_in") OffsetDateTime askedIn, String answered, @Field("answered_in") OffsetDateTime answeredIn,
        String expected) {

    public Boolean isAnswered() {
        return Objects.nonNull(answeredIn);
    }

    public Boolean isNotAnswered() {
        return Objects.isNull(answeredIn);
    }

    public Boolean isCorrect() {
        return isAnswered() && answered.equals(expected);
    }
    
    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public QuestionBuilder toBuilder() {
        return new QuestionBuilder(asked, askedIn, answered, answeredIn, expected);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionBuilder{
        private String asked;
        private OffsetDateTime askedIn;
        private String answered;
        private OffsetDateTime answeredIn;
        private String expected;

        public QuestionBuilder asked(final String asked) {
            if (StringUtils.isNotBlank(asked)) {
                this.asked = asked;
                this.askedIn = OffsetDateTime.now();
            }
            return this;
        }

        public QuestionBuilder answered(final String answered) {
            if (StringUtils.isNotBlank(answered)) {
                this.answered = answered;
                this.answeredIn = OffsetDateTime.now();
            }
            return this;
        }

        public QuestionBuilder expected(final String expected) {
            this.expected = expected;
            return this;
        }

        public Question build() {
            return new Question(asked, askedIn, answered, answeredIn, expected);
        }
    }
}
