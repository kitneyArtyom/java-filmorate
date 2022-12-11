package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;

public interface FilmStorage {
    Film create(String name, String description, LocalDate releaseDate, Integer duration);

    Film update(Film film);

    void delete(Film film);

    Film findById(int id);

    List<Film> getFilms();
}
