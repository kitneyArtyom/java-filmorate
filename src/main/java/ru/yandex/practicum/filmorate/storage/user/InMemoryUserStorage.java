package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private int idsCount;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        idsCount = 1;
    }

    @Override
    public User create(User user) {
        user.setId(idsCount++);
        user.setFriendsIds(Set.of());

        return users.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new StorageNotFoundException("user not found");
        }

        return users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> getFriends(User user) {
        if (!users.containsKey(user.getId())) {
            throw new StorageNotFoundException("user not found");
        }

        return user.getFriendsIds().stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getSharedFriends(User user1, User user2) {
        return user1.getFriendsIds().stream()
                .filter(user2.getFriendsIds()::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public void addFriend(User user, User friend) {
        if (!users.containsKey(user.getId()) || users.containsKey(friend.getId())) {
            throw new StorageNotFoundException("user not found");
        }

        user = users.get(user.getId());
        Set<Integer> newIds = new HashSet<>(user.getFriendsIds());
        newIds.add(friend.getId());
        user.setFriendsIds(Set.copyOf(newIds));
    }

    @Override
    public void deleteFriend(User user, User friend) {
        if (!users.containsKey(user.getId()) || users.containsKey(friend.getId())) {
            throw new StorageNotFoundException("user not found");
        }

        user = users.get(user.getId());
        Set<Integer> newIds = new HashSet<>(user.getFriendsIds());
        newIds.remove(friend.getId());
        user.setFriendsIds(Set.copyOf(newIds));
    }
}
