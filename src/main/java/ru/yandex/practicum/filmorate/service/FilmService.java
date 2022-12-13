package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmsStorage;

    public FilmService(InMemoryFilmStorage filmsStorage) {
        this.filmsStorage = filmsStorage;
    }

    public Film createFilm(@NotNull Film film) {
        log.trace("create film: [{}] {}", film.getId(), film.getName());
        return filmsStorage.create(film);
    }

    public Film updateFilm(@NotNull Film film) {
        log.trace("update film: [{}] {}", film.getId(), film.getName());
        return filmsStorage.update(film);
    }

    public void deleteFilm(@NotNull Film film) {
        filmsStorage.delete(film);
    }

    public Optional<Film> findById(int id) {
        return filmsStorage.findById(id);
    }

    public List<Film> getFilms() {
        return filmsStorage.getFilms();
    }

    public List<Film> getTopFilms(int count) {
        return filmsStorage.getTopFilms(count);
    }

    public void addLike(Film film, User user) {
        filmsStorage.addLike(film, user);
    }

    public void removeLike(Film film, User user) {
        filmsStorage.removeLike(film, user);
    }
}
