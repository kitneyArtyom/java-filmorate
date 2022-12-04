package ru.yandex.practicum.filmorate.dto.request;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@FilmConstraint
public class FilmRequestDto {
    @NotBlank
    String name;

    @NotBlank
    @Size(max = 200)
    String description;

    @NotNull
    LocalDate releaseDate;

    @NotNull
    @Min(1)
    Integer duration;
}
