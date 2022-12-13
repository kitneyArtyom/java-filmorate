package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.web.dto.request.UserRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.UserResponseDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.web.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
@RequestMapping(
        path = "/users",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getUsers() {
        return userService.getUsers().stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto createUser(@RequestBody @Valid @NotNull UserRequestDto userDto) {
        User user = userMapper.mapToUser(userDto);
        user = userService.createUser(user);
        return userMapper.mapToUserResponse(user);
    }

    @GetMapping(path = "/{id:.+}")
    public UserResponseDto getUser(@PathVariable Integer id) {
        Optional<User> optUser = userService.findUserById(id);

        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        return userMapper.mapToUserResponse(optUser.get());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto updateUser(@RequestBody @Valid @NotNull UserRequestDto userDto) {
        User user = userMapper.mapToUser(userDto);
        user = userService.updateUser(user);
        return userMapper.mapToUserResponse(user);
    }

    @PutMapping(path = "/{id:.+}/friends/{friendId:.+}")
    public UserResponseDto addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        Optional<User> optUser = userService.findUserById(id);
        Optional<User> optFriend = userService.findUserById(friendId);

        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        if (optFriend.isEmpty()) {
            throw new NotFoundException("friend's id not found");
        }

        User user = userService.addFriend(optUser.get(), optFriend.get());
        return userMapper.mapToUserResponse(user);
    }

    @DeleteMapping(path ="/{id:.+}/friends/{friendId:.+}")
    public UserResponseDto deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        Optional<User> optUser = userService.findUserById(id);
        Optional<User> optFriend = userService.findUserById(friendId);

        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        if (optFriend.isEmpty()) {
            throw new NotFoundException("friend's id not found");
        }

        User user = userService.deleteFriend(optUser.get(), optFriend.get());
        return userMapper.mapToUserResponse(user);
    }

    @GetMapping("/{id:.+}/friends")
    public List<UserResponseDto> getFriends(@PathVariable Integer id) {
        Optional<User> optUser = userService.findUserById(id);

        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        return userService.getFriends(optUser.get()).stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:.+}/friends/common/{otherId:.+}")
    public List<UserResponseDto> getFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        Optional<User> optUser = userService.findUserById(id);
        Optional<User> optOtherUser = userService.findUserById(otherId);

        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        if (optOtherUser.isEmpty()) {
            throw new NotFoundException("other user not found");
        }

        return userService.getSharedFriends(optUser.get(), optOtherUser.get()).stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }
}
