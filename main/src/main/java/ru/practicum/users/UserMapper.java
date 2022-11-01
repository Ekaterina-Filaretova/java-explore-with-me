package ru.practicum.users;

import org.springframework.stereotype.Component;
import ru.practicum.users.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public List<UserDto> convertToDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }
        return userDtos;
    }

    public User convertToEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }
}