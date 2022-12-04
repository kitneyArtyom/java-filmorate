package ru.yandex.practicum.filmorate.dto.request;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.UserConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@UserConstraint
public class UserRequestDto {
    String name;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String login;

    @NotNull
    LocalDate birthday;
}
