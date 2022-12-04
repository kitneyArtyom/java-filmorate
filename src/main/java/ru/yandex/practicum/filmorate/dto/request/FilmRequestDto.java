package ru.yandex.practicum.filmorate.dto.request;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@FilmConstraint
public class FilmRequestDto {
    @Min(0)
    Integer id;

    @NotBlank(message = "name не должен быть пустым")
    String name;

    @Size(max = 200, message = "description недолжен превыщать длину в 200 символов")
    @NotBlank(message = "description не должен быть пустым")
    String description;

    @NotNull(message = "releaseDate не должен быть null")
    LocalDate releaseDate;

    @Min(value = 1, message = "duration должен больше нуля")
    @NotNull(message = "duration не должен быть null")
    Integer duration;
}
