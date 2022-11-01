package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.users.dto.UserDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers(Long[] ids, Integer from, Integer size) {
        List<User> users;
        Pageable pageable = PageRequest.of(from, size, Sort.unsorted());
        users = ids == null ? userRepository.findAll(pageable).getContent()
                : userRepository.findAllById(List.of(ids));
        log.info("Найдены пользователи {}", users);
        return userMapper.convertToDto(users);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userRepository.save(user);
        log.info("Новый пользователь сохранен {}", user);
        return userMapper.convertToDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь удален {}", user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Отсутствует пользователь с id " + userId));
    }
}
