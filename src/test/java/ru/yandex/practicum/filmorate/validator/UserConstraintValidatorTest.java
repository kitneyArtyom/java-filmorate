package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.web.dto.request.UserRequestDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.validator.utils.ValidateTestUtils.createUserDto;
import static ru.yandex.practicum.filmorate.validator.utils.ValidateTestUtils.dtoHasErrorMessage;

@DisplayName("Валидация UserRequestDto")
class UserConstraintValidatorTest {

    @Test
    @DisplayName("Валидация email")
    void validationEmailProperty() {
        UserRequestDto validEmail = createUserDto("Frodo Begins", "frodo", LocalDate.of(2000, 1, 1), "frodobeg@gmail.com");
        UserRequestDto invalidEmail = createUserDto("Frodo Begins", "frodo", LocalDate.of(2000, 1, 1), "frodobeggmail.com");
        UserRequestDto blankEmail = createUserDto("Frodo Begins", "frodo", LocalDate.of(2000, 1, 1), "");

        assertAll(
                () -> assertFalse(dtoHasErrorMessage(validEmail, "email должен соответствовать формату Email")),
                () -> assertTrue(dtoHasErrorMessage(invalidEmail, "email должен соответствовать формату Email")),
                () -> assertTrue(dtoHasErrorMessage(blankEmail, "email не должен быть пустым"))
        );
    }

    @Test
    @DisplayName("Валидация login")
    void validationLoginProperty() {
        UserRequestDto validLogin = createUserDto("Frodo Begins", "frodo", LocalDate.of(2000, 1, 1), "frodobeg@gmail.com");
        UserRequestDto invalidLogin = createUserDto("Frodo Begins", "fro do", LocalDate.of(2000, 1, 1), "frodobeg@gmail.com");
        UserRequestDto blankLogin = createUserDto("Frodo Begins", "", LocalDate.of(2000, 1, 1), "");

        assertAll(
                () -> assertFalse(dtoHasErrorMessage(validLogin, "login содержит пробелы")),
                () -> assertTrue(dtoHasErrorMessage(invalidLogin, "login содержит пробелы")),
                () -> assertTrue(dtoHasErrorMessage(blankLogin, "login не должен быть пустым"))
        );
    }

    @Test
    @DisplayName("Валидация birthday")
    void validationBirthdayProperty() {
        UserRequestDto validBirthday = createUserDto("Frodo Begins", "frodo", LocalDate.of(2000, 1, 1), "frodobeg@gmail.com");
        UserRequestDto blankBirthday = createUserDto("Frodo Begins", "frodo", null, "frodobeg@gmail.com");

        assertAll(
                () -> assertFalse(dtoHasErrorMessage(validBirthday, "birthday не должен быть null")),
                () -> assertTrue(dtoHasErrorMessage(blankBirthday, "birthday не должен быть null"))
        );
    }
}