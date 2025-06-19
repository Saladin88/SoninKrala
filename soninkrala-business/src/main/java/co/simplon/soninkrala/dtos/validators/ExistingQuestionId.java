package co.simplon.soninkrala.dtos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ExistingQuestionIdValidator.class)
public @interface ExistingQuestionId {
    String message() default "Question id doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
