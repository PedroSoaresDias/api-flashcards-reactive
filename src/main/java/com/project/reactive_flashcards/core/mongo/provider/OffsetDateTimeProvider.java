package com.project.reactive_flashcards.core.mongo.provider;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static java.time.ZoneOffset.UTC;

@Component("dateTimeProvider")
public class OffsetDateTimeProvider implements DateTimeProvider {

    @Override
    public @NonNull Optional<TemporalAccessor> getNow() {
        return Optional.of(OffsetDateTime.now(UTC));
    }

}
