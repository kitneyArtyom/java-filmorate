package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.request.UserRequestDto;
import ru.yandex.practicum.filmorate.dto.resonse.UserResponseDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path="/users",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    UserMapper userMapper;

    Map<Integer, User> users;
    int idsCount;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.users = new HashMap<>();
        this.idsCount = 0;
    }

    @GetMapping
    public List<UserResponseDto> getUsers() {
        return users.values().stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserRequestDto userDto) {
        User user = userMapper.mapToUser(userDto);
        user.setId(idsCount++);
        users.put(user.getId(), user);
    }

    @PutMapping(path = "/{id:.+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody UserRequestDto userDto, @PathVariable Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("user not found");
        }

        User user = userMapper.mapToUser(userDto);
        user.setId(id);
        users.put(user.getId(), user);
    }
}
