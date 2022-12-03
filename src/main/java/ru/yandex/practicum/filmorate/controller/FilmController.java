package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.request.FilmRequestDto;
import ru.yandex.practicum.filmorate.dto.resonse.FilmResponseDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/films",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmController {

    FilmMapper filmMapper;

    Map<Integer, Film> films;
    int idsCount;

    public FilmController(FilmMapper filmMapper) {
        this.films = new HashMap<>();
        this.idsCount = 0;
        this.filmMapper = filmMapper;
    }

    @GetMapping
    public List<FilmResponseDto> getFilms() {
        return films.values().stream()
                .map(filmMapper::mapToFilmResponse)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createFilm(@RequestBody FilmRequestDto filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film.setId(idsCount++);
        films.put(film.getId(), film);
    }

    @PutMapping(path = "/{id:.+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateFilm(@RequestBody FilmRequestDto filmDto, @PathVariable Integer id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("film not found");
        }

        Film film = filmMapper.mapToFilm(filmDto);
        film.setId(id);
        films.put(film.getId(), film);
    }
}