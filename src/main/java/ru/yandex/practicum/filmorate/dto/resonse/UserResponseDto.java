package ru.yandex.practicum.filmorate.dto.resonse;

import lombok.Data;

@Data
public class UserResponseDto {
    String id;
    String name;
    String email;
    String login;
    String birthday;
}
