package ru.yandex.practicum.filmorate.web.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String login;
    private String birthday;
}
