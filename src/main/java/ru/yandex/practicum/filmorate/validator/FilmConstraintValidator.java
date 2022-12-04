package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.dto.request.FilmRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmConstraintValidator implements ConstraintValidator<FilmConstraint, FilmRequestDto> {
    private static final LocalDate BIRTHDAY_FILMS = LocalDate.of(1895, 12, 28);
    private static final String RELEASE_DATE_INVALID_MESSAGE = "releaseDate меньше чем дата рождения кино";

    @Override
    public boolean isValid(FilmRequestDto filmDto, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        boolean isReleaseDateValid = filmDto.getReleaseDate().isAfter(BIRTHDAY_FILMS);

        if (!isReleaseDateValid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(RELEASE_DATE_INVALID_MESSAGE)
                    .addConstraintViolation();
        }

        return isReleaseDateValid;
    }
}
