package ru.yandex.practicum.filmorate.web.dto.request;

import lombok.Data;
import ru.yandex.practicum.filmorate.web.validator.FilmConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@FilmConstraint
public class FilmRequestDto {
    @Min(0)
    private Integer id;

    @NotBlank(message = "name не должен быть пустым")
    private String name;

    @Size(max = 200, message = "description недолжен превыщать длину в 200 символов")
    @NotBlank(message = "description не должен быть пустым")
    private String description;

    @NotNull(message = "releaseDate не должен быть null")
    private LocalDate releaseDate;

    @Min(value = 1, message = "duration должен больше нуля")
    @NotNull(message = "duration не должен быть null")
    private Integer duration;
}
