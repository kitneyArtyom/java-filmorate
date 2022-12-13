package ru.yandex.practicum.filmorate.web.dto.response;

import lombok.Data;

@Data
public class FilmResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String releaseDate;
    private Integer duration;
}
