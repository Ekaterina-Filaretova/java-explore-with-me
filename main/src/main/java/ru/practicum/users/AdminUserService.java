package ru.practicum.users;

import ru.practicum.users.dto.UserDto;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getUsers(Long[] ids, Integer from, Integer size);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);

    User getUserById(Long userId);
}
