package ru.yandex.practicum.filmorate.dto.resonse;

import lombok.Data;

@Data
public class FilmResponseDto {
    Integer id;
    String name;
    String description;
    String releaseDate;
    String duration;
}
