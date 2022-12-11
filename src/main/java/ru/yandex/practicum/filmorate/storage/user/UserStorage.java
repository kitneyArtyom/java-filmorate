package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserStorage {
    User create(String name, String email, String login, LocalDate birthday);

    User update(User user);

    void delete(User user);

    User findById(int id);

    List<User> getUsers();
}
