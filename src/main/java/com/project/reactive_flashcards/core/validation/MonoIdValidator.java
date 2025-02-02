package com.project.reactive_flashcards.core.validation;

import org.bson.types.ObjectId;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MonoIdValidator implements ConstraintValidator<MongoId, String>{

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        
        return StringUtils.isNotBlank(value) && ObjectId.isValid(value);
    }

}
