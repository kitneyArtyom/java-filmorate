package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private int ids;

    public InMemoryFilmStorage() {
        this.films = new HashMap<>();
        this.ids = 1;
    }

    @Override
    public Film create(Film film) {
        film.setId(ids++);

        return this.films.put(film.getId(), film);
    }

    @Override
    public Film update(Film film) {
        if (!this.films.containsKey(film.getId())) {
            throw new StorageNotFoundException("film not found");
        }

        return this.films.put(film.getId(), film);
    }

    @Override
    public void delete(Film film) {
        this.films.remove(film.getId());
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void addLike(Film film, User user) {
        if (!this.films.containsKey(film.getId())) {
            throw new StorageNotFoundException("film not found");
        }

        film = films.get(film.getId());
        Set<Integer> ids = new HashSet<>(film.getLikedUsersIds());
        ids.add(user.getId());
        film.setLikedUsersIds(Set.copyOf(ids));
    }

    @Override
    public void removeLike(Film film, User user) {
        if (!this.films.containsKey(film.getId())) {
            throw new StorageNotFoundException("film not found");
        }

        film = films.get(film.getId());
        Set<Integer> ids = new HashSet<>(film.getLikedUsersIds());
        ids.remove(user.getId());
        film.setLikedUsersIds(Set.copyOf(ids));
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return films.values().stream()
                .sorted(Comparator.comparingInt(film -> film.getLikedUsersIds().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
