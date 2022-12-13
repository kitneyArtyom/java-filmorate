package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private int idsCount;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        idsCount = 1;
    }

    @Override
    public User add(User user) {
        user.setId(idsCount++);
        user.setFriendsIds(Set.of());
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new EntityNotFoundException("user not found");
        }

        User savedUser = users.get(user.getId());
        user.setFriendsIds(savedUser.getFriendsIds());

        users.put(user.getId(), user);
        return user;
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
            throw new EntityNotFoundException("user not found");
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
    public User addFriend(User user, User friend) {
        if (!users.containsKey(user.getId()) || !users.containsKey(friend.getId())) {
            throw new EntityNotFoundException("user not found");
        }

        Stream.of(List.of(user, friend), List.of(friend, user))
                .forEach(pair -> {
                    User _user = users.get(pair.get(0).getId());
                    User _friend = pair.get(1);

                    Set<Integer> newIds = new HashSet<>(_user.getFriendsIds());
                    newIds.add(_friend.getId());
                    _user.setFriendsIds(Set.copyOf(newIds));
                    users.put(_user.getId(), _user);
                });

        return users.get(user.getId());
    }

    @Override
    public User deleteFriend(User user, User friend) {
        if (!users.containsKey(user.getId()) || !users.containsKey(friend.getId())) {
            throw new EntityNotFoundException("user not found");
        }

        Stream.of(List.of(user, friend), List.of(friend, user))
                .forEach(pair -> {
                    User _user = users.get(pair.get(0).getId());
                    User _friend = pair.get(1);

                    Set<Integer> newIds = new HashSet<>(_user.getFriendsIds());
                    newIds.remove(_friend.getId());
                    _user.setFriendsIds(Set.copyOf(newIds));
                    users.put(_user.getId(), _user);
                });

        return users.get(user.getId());
    }
}
