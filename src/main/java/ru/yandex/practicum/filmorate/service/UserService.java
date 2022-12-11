package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserStorage userStorage;

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(@NotNull User user) {
        return userStorage.create(user);
    }

    public User updateUser(@NotNull User user) {
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

    public void addFriend(@NotNull User user, @NotNull User friend) {
        userStorage.addFriend(user, friend);
    }

    public List<User> getFriends(@NotNull User user) {
        return userStorage.getFriends(user);
    }

    List<User> getSharedFriends(@NotNull User user1, @NotNull User user2) {
        return userStorage.getSharedFriends(user1, user2);
    }

    void deleteFriend(@NotNull User user, @NotNull User friend) {
        userStorage.deleteFriend(user, friend);
    }
}
