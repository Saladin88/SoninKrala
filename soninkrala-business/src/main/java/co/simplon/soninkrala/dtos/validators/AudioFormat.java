package co.simplon.soninkrala.dtos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AudioSizeValidator.class)
public @interface AudioFormat {
    String message() default "Size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
