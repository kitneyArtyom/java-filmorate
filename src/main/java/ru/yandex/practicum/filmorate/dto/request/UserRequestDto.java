package ru.yandex.practicum.filmorate.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
    String name;
    String email;
    String login;
    String birthday;
}
