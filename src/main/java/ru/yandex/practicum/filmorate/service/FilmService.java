package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmStorage films;

    public FilmService(InMemoryFilmStorage films) {
        this.films = films;
    }

    public Film createFilm(@NotNull Film film) {
        return films.create(film);
    }

    public Film updateFilm(@NotNull Film film) {
        return films.update(film);
    }

    public void deleteFilm(@NotNull Film film) {
        films.delete(film);
    }

    Optional<Film> findById(int id) {
        return films.findById(id);
    }

    List<Film> getFilms() {
        return films.getFilms();
    }

    List<Film> getTopFilms(int count) {
        return films.getTopFilms(count);
    }
}
