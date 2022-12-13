package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
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
        film.setLikedUsersIds(Set.of());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Optional<Film> optFilm = findById(film.getId());

        if (optFilm.isEmpty()) {
            throw new EntityNotFoundException("film not found");
        }

        film.setLikedUsersIds(optFilm.get().getLikedUsersIds());
        films.put(film.getId(), film);

        return film;
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
            throw new EntityNotFoundException("film not found");
        }

        film = films.get(film.getId());
        Set<Integer> ids = new HashSet<>(film.getLikedUsersIds());
        ids.add(user.getId());
        film.setLikedUsersIds(Set.copyOf(ids));
        films.put(film.getId(), film);
    }

    @Override
    public void removeLike(Film film, User user) {
        if (!this.films.containsKey(film.getId())) {
            throw new EntityNotFoundException("film not found");
        }

        film = films.get(film.getId());
        Set<Integer> ids = new HashSet<>(film.getLikedUsersIds());
        ids.remove(user.getId());
        film.setLikedUsersIds(Set.copyOf(ids));
        films.put(film.getId(), film);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        if (count > 0) {
            return films.values().stream()
                    .sorted(Comparator.comparingInt(film -> film.getLikedUsersIds().size() * -1))
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            return films.values().stream()
                    .sorted(Comparator.comparingInt(film -> film.getLikedUsersIds().size()))
                    .collect(Collectors.toList());
        }
    }
}
