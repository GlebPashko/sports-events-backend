package com.sportsevents.backend.sportseventsbackend.authorization.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = FieldMatchValidator.class)
@Target({TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RUNTIME)
public @interface FieldMatch {
    String message() default "Password and repeat password don't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String first();
    String second();
}
