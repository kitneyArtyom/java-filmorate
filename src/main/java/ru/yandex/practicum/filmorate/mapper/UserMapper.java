package ru.yandex.practicum.filmorate.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.request.UserRequestDto;
import ru.yandex.practicum.filmorate.dto.resonse.UserResponseDto;
import ru.yandex.practicum.filmorate.model.User;

@Mapper
public interface UserMapper {

    User mapToUser(UserRequestDto dto);
    UserResponseDto mapToUserResponse(User user);
}
