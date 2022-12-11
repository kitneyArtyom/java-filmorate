package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    User update(User user);

    void delete(User user);

    Optional<User> findById(int id);

    List<User> getUsers();

    List<User> getFriends(User user);

    List<User> getSharedFriends(User user1, User user2);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);
}
