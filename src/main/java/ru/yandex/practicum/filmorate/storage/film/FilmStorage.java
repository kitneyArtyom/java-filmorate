package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    void delete(Film film);

    Optional<Film> findById(int id);

    List<Film> getFilms();

    void addLike(Film film, User user);

    void removeLike(Film film, User user);

    List<Film> getTopFilms(int count);
}
