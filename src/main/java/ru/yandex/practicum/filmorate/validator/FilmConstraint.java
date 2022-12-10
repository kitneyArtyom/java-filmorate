package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilmConstraintValidator.class)
public @interface FilmConstraint {
    String message() default "Invalid film data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
