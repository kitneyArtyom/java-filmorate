package ru.yandex.practicum.filmorate.validator.utils;

import ru.yandex.practicum.filmorate.dto.request.FilmRequestDto;
import ru.yandex.practicum.filmorate.dto.request.UserRequestDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

public class ValidateTestUtils {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> boolean dtoHasErrorMessage(T dto, String message) {
        Set<ConstraintViolation<T>> errors = VALIDATOR.validate(dto);
        return errors.stream().map(ConstraintViolation::getMessage).anyMatch(message::equals);
    }

    public static UserRequestDto createUserDto(String name, String login, LocalDate birthday, String email) {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setName(name);
        userDto.setLogin(login);
        userDto.setBirthday(birthday);
        userDto.setEmail(email);

        return userDto;
    }

    public static FilmRequestDto createFilmDto(String name, String description, LocalDate releaseDate, Integer duration) {
        FilmRequestDto filmDto = new FilmRequestDto();
        filmDto.setName(name);
        filmDto.setDescription(description);
        filmDto.setReleaseDate(releaseDate);
        filmDto.setDuration(duration);

        return filmDto;
    }
}
