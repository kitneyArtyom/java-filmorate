package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    public InMemoryFilmStorage() {
        this.filmStorage = new HashMap<>();
        this.ids = 1;
    }

    private final Map<Integer, Film> filmStorage;
    private int ids;

    @Override
    public Film create(String name, String description, LocalDate releaseDate, Integer duration) {
        Film film = new Film();
        film.setId(ids++);
        film.setName(name);
        film.setDescription(description);
        film.setReleaseDate(releaseDate);
        film.setDuration(duration);

        return filmStorage.put(film.getId(), film);
    }

    @Override
    public Film update(Film film) {
        if (!filmStorage.containsKey(film.getId())) {
            throw new StorageNotFoundException("film not found");
        }

        return filmStorage.put(film.getId(), film);
    }

    @Override
    public void delete(Film film) {
        filmStorage.remove(film.getId());
    }

    @Override
    public Film findById(int id) {
        return filmStorage.get(id);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(filmStorage.values());
    }
}
