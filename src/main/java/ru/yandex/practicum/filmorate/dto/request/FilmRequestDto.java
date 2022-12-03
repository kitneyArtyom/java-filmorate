package ru.yandex.practicum.filmorate.dto.request;

import lombok.Data;

@Data
public class FilmRequestDto {
    String name;
    String description;
    String releaseDate;
    String duration;
}
