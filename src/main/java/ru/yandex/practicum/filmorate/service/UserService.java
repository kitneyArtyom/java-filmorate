package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    UserStorage userStorage;

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(@NotNull User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        log.debug("create user: [{}] {}", user.getId(), user.getLogin());

        return userStorage.add(user);
    }

    public User updateUser(@NotNull User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        log.debug("update user: [{}] {}", user.getId(), user.getLogin());

        return userStorage.update(user);
    }

    public void deleteUser(@NotNull User user) {
        userStorage.delete(user);
    }

    public Optional<User> findUserById(int id) {
        return userStorage.findById(id);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addFriend(@NotNull User user, @NotNull User friend) {
        return userStorage.addFriend(user, friend);
    }

    public List<User> getFriends(@NotNull User user) {
        return userStorage.getFriends(user);
    }

    public List<User> getSharedFriends(@NotNull User user1, @NotNull User user2) {
        return userStorage.getSharedFriends(user1, user2);
    }

    public User deleteFriend(@NotNull User user, @NotNull User friend) {
        return userStorage.deleteFriend(user, friend);
    }
}
