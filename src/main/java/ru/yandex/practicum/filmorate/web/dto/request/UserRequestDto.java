package ru.yandex.practicum.filmorate.web.dto.request;

import lombok.Data;
import ru.yandex.practicum.filmorate.web.validator.UserConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@UserConstraint
public class UserRequestDto {
    @Min(0)
    private Integer id;

    private String name;

    @Email(message = "email должен соответствовать формату Email")
    @NotBlank(message = "email не должен быть пустым")
    private String email;

    @NotBlank(message = "login не должен быть пустым")
    private String login;

    @NotNull(message = "birthday не должен быть null")
    private LocalDate birthday;
}
