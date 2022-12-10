package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.request.FilmRequestDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.validator.utils.ValidateTestUtils.*;

@DisplayName("Валидация FilmRequestDto")
class FilmConstraintValidatorTest {

    @Test
    @DisplayName("Валидация name")
    void validationNameProperty() {
        FilmRequestDto validName = createFilmDto("Властели колец", "Некое описание", LocalDate.of(2001, 12, 10), 178);
        FilmRequestDto blankName = createFilmDto("", "Некое описание", LocalDate.of(2001, 12, 10), 178);

        assertAll(
                () -> assertFalse(dtoHasErrorMessage(validName, "name не должен быть пустым")),
                () -> assertTrue(dtoHasErrorMessage(blankName, "name не должен быть пустым"))
        );
    }

    @Test
    @DisplayName("Валидация description")
    void validationDescriptionProperty() {
        String oversizeString = "yep".repeat(100);

        FilmRequestDto validDescription = createFilmDto("Властели колец", "Некое описание", LocalDate.of(2001, 12, 10), 178);
        FilmRequestDto invalidDescription = createFilmDto("Властели колец", oversizeString, LocalDate.of(2001, 12, 10), 178);
        FilmRequestDto blankDescription = createFilmDto("Властели колец", "", LocalDate.of(2001, 12, 10), 178);

        assertAll(
                () -> assertFalse(dtoHasErrorMessage(validDescription, "description недолжен превыщать длину в 200 символов")),
                () -> assertTrue(dtoHasErrorMessage(invalidDescription, "description недолжен превыщать длину в 200 символов")),
                () -> assertTrue(dtoHasErrorMessage(blankDescription, "description не должен быть пустым"))
        );
    }

    @Test
    @DisplayName("Валидация releaseDate")
    void validationReleaseDateProperty() {
        FilmRequestDto validReleaseDate = createFilmDto("Властели колец", "Некое описание", LocalDate.of(2001, 12, 10), 178);
        FilmRequestDto invalidReleaseDate = createFilmDto("Властели колец", "Некое описание", LocalDate.of(1888, 12, 10), 178);
        FilmRequestDto blankReleaseDate = createFilmDto("Властели колец", "Некое описание", null, 178);

        assertAll(
                () -> assertFalse(dtoHasErrorMessage(validReleaseDate, "releaseDate меньше чем дата рождения кино")),
                () -> assertTrue(dtoHasErrorMessage(invalidReleaseDate, "releaseDate меньше чем дата рождения кино")),
                () -> assertTrue(dtoHasErrorMessage(blankReleaseDate, "releaseDate не должен быть null"))
        );
    }

    @Test
    @DisplayName("Валидация duration")
    void validationDurationProperty() {
        FilmRequestDto validDescription = createFilmDto("Властели колец", "Некое описание", LocalDate.of(2001, 12, 10), 178);
        FilmRequestDto invalidDescription = createFilmDto("Властели колец", "Некое описание", LocalDate.of(2001, 12, 10), 0);
        FilmRequestDto blankDescription = createFilmDto("Властели колец", "Некое описание", LocalDate.of(2001, 12, 10), null);

        assertAll(
                () -> assertFalse(dtoHasErrorMessage(validDescription, "duration должен больше нуля")),
                () -> assertTrue(dtoHasErrorMessage(invalidDescription, "duration должен больше нуля")),
                () -> assertTrue(dtoHasErrorMessage(blankDescription, "duration не должен быть null"))
        );
    }
}