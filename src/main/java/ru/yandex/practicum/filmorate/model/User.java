package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    Integer id;
    String name;
    String email;
    String login;
    LocalDate birthday;
    Set<Integer> friendsIds;
}
