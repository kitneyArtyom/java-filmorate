package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> userStorage;
    private int ids;

    public InMemoryUserStorage() {
        userStorage = new HashMap<>();
        ids = 1;
    }

    @Override
    public User create(String name, String email, String login, LocalDate birthday) {
        User user = new User();
        user.setId(ids++);
        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);
        user.setBirthday(birthday);

        return userStorage.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        if (!userStorage.containsKey(user.getId())) {
            throw new StorageNotFoundException("film not found");
        }

        return userStorage.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        userStorage.remove(user.getId());
    }

    @Override
    public User findById(int id) {
        return userStorage.get(id);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userStorage.values());
    }
}
