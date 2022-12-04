package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.request.FilmRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Slf4j
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
            log.debug("[film.name: {}] releaseDate not valid", filmDto.getName());
        }

        return isReleaseDateValid;
    }
}
