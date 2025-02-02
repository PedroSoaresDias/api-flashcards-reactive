package com.project.reactive_flashcards.core.mongo.converter;

import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class DateToOffsetDateTimeConverter implements Converter<OffsetDateTime, Date> {

    @Override
    @Nullable
    public Date convert(@NonNull final OffsetDateTime source) {
        return Date.from(source.toInstant());
    }

}
