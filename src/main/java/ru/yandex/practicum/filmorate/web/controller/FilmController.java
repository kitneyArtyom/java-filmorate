package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.web.dto.request.FilmRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.FilmResponseDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.web.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/films", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmController {
    private final FilmMapper filmMapper;
    private final Map<Integer, Film> films;
    private int idsCount;

    public FilmController(FilmMapper filmMapper) {
        this.films = new HashMap<>();
        this.idsCount = 1;
        this.filmMapper = filmMapper;
    }

    @GetMapping
    public List<FilmResponseDto> getFilms() {
        return films.values().stream().map(filmMapper::mapToFilmResponse).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public FilmResponseDto createFilm(@Valid @NotNull @RequestBody FilmRequestDto filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film.setId(idsCount++);
        films.put(film.getId(), film);
        log.trace("create film: [{}] {}", film.getId(), film.getName());

        return filmMapper.mapToFilmResponse(film);
    }

    @PutMapping(path = "/{id:.+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FilmResponseDto updateFilm(@Valid @NotNull @RequestBody FilmRequestDto filmDto, @PathVariable Integer id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("film not found");
        }

        Film film = filmMapper.mapToFilm(filmDto);
        film.setId(id);
        films.put(film.getId(), film);
        log.trace("update film: [{}] {}", film.getId(), film.getName());

        return filmMapper.mapToFilmResponse(film);
    }

    // Для postman тестов
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public FilmResponseDto anotherUpdateFilm(@Valid @NotNull @RequestBody FilmRequestDto filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);

        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("film not found");
        }

        films.put(film.getId(), film);
        log.trace("update film: [{}] {}", film.getId(), film.getName());
        return filmMapper.mapToFilmResponse(film);
    }
}
