package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.request.UserRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Slf4j
public class UserConstraintValidator implements ConstraintValidator<UserConstraint, UserRequestDto> {
    private static final String LOGIN_INVALID_MESSAGE = "login содержит пробелы";
    private static final String BIRTHDAY_INVALID_MESSAGE = "birthday больше чем текущая дата";

    @Override
    public boolean isValid(UserRequestDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        boolean isLoginValid = userDto.getLogin() == null || !userDto.getLogin().contains(" ");
        boolean isBirthdayValid = userDto.getBirthday() == null || userDto.getBirthday().isBefore(LocalDate.now());

        if (!isLoginValid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(LOGIN_INVALID_MESSAGE)
                    .addConstraintViolation();
            log.debug("[user.login: {}] login not valid {{}}", userDto.getLogin(), userDto.getLogin());
        }

        if (!isBirthdayValid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(BIRTHDAY_INVALID_MESSAGE)
                    .addConstraintViolation();
            log.debug("[user.login: {}] birthday not valid {{}}", userDto.getLogin(), userDto.getBirthday());
        }

        return isLoginValid && isBirthdayValid;
    }
}
