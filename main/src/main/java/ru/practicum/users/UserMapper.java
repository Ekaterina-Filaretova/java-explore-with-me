package ru.practicum.users;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.follows.SubscriptionMapper;
import ru.practicum.users.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {

    private SubscriptionMapper subscriptionMapper;

    public UserDto convertToDto(User user) {
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.isFollowed(),
                subscriptionMapper.convertToDto(user.getSubscriptions()));
    }

    public List<UserDto> convertToDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToDto(user));
        }
        return userDtos;
    }

    public User convertToEntity(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                true,
                new ArrayList<>());
    }
}