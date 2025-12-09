package ru.rutmiit.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueChatNameValidator.class)
public @interface UniqueChatName {
    String message() default "Название чата уже занято!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
