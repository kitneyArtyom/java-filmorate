package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.web.dto.request.FilmRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.FilmResponseDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.web.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/films", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmController {
    private final FilmMapper filmMapper;
    private final FilmService filmService;
    private final UserService userService;

    public FilmController(FilmMapper filmMapper, FilmService filmService, UserService userService) {
        this.filmMapper = filmMapper;
        this.filmService = filmService;
        this.userService = userService;
    }

    @GetMapping(path = "/{id:.+}")
    public FilmResponseDto getFilm(@PathVariable int id) {
        Optional<Film> film = filmService.findById(id);

        if (film.isEmpty()) {
            throw new NotFoundException("film not found");
        }

        return filmMapper.mapToFilmResponse(film.get());
    }

    @GetMapping
    public List<FilmResponseDto> getFilms() {
        return filmService.getFilms().stream()
                .map(filmMapper::mapToFilmResponse)
                .collect(Collectors.toList());
    }
    @GetMapping("/popular")
    public List<FilmResponseDto> getPopularFilms(@RequestParam(required = false, defaultValue = "0") int count) {
        return filmService.getTopFilms(count).stream()
                .map(filmMapper::mapToFilmResponse)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public FilmResponseDto createFilm(@Valid @NotNull @RequestBody FilmRequestDto filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film = filmService.createFilm(film);
        return filmMapper.mapToFilmResponse(film);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public FilmResponseDto updateFilm(@Valid @NotNull @RequestBody FilmRequestDto filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film = filmService.updateFilm(film);
        return filmMapper.mapToFilmResponse(film);
    }

    @PutMapping(path = "{id:.+}/like/{userId}")
    public FilmResponseDto addLike(@PathVariable int id, @PathVariable int userId) {
        Optional<Film> optFilm = filmService.findById(id);
        Optional<User> optUser = userService.findUserById(userId);

        if (optFilm.isEmpty()) {
            throw new NotFoundException("film not found");
        }

        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        filmService.addLike(optFilm.get(), optUser.get());

        return filmService.findById(id)
                .map(filmMapper::mapToFilmResponse)
                .orElseThrow(() -> new NotFoundException("film not found"));
    }

    @DeleteMapping(path = "{id:.+}/like/{userId}")
    public FilmResponseDto removeLike(@PathVariable int id, @PathVariable int userId) {
        Optional<Film> optFilm = filmService.findById(id);
        Optional<User> optUser = userService.findUserById(userId);

        if (optFilm.isEmpty()) {
            throw new NotFoundException("film not found");
        }

        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        filmService.removeLike(optFilm.get(), optUser.get());

        return filmService.findById(id)
                .map(filmMapper::mapToFilmResponse)
                .orElseThrow(() -> new NotFoundException("film not found"));
    }
}
