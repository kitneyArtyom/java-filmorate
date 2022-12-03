package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    Integer id;
    String name;
    String email;
    String login;
    LocalDate birthday;
}
