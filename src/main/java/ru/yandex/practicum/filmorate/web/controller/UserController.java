package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.web.dto.request.UserRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.UserResponseDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.web.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<Integer, User> users;
    private int idsCount;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.users = new HashMap<>();
        this.idsCount = 1;
    }

    @GetMapping
    public List<UserResponseDto> getUsers() {
        return users.values().stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto createUser(@RequestBody @Valid @NotNull UserRequestDto userDto) {
        User user = userMapper.mapToUser(userDto);
        user.setId(idsCount++);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.trace("create user: [{}] {}", user.getId(), user.getLogin());

        return userMapper.mapToUserResponse(user);
    }

    @PutMapping(path = "/{id:.+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto updateUser(@RequestBody @Valid @NotNull UserRequestDto userDto, @PathVariable Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("user not found");
        }

        User user = userMapper.mapToUser(userDto);
        user.setId(id);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.trace("update user: [{}] {}", user.getId(), user.getLogin());

        return userMapper.mapToUserResponse(user);
    }

    //Для postman тестов
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto anotherUpdateUser(@RequestBody @Valid @NotNull UserRequestDto userDto) {
        User user = userMapper.mapToUser(userDto);

        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("user not found");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.trace("update user: [{}] {}", user.getId(), user.getLogin());

        return userMapper.mapToUserResponse(user);
    }
}
