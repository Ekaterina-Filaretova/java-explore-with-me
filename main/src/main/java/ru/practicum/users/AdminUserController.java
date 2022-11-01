package ru.practicum.users;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/admin/users")
@AllArgsConstructor
public class AdminUserController {

    private final AdminUserService adminService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) Long[] ids,
                                  @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                                  @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return adminService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return adminService.addUser(userDto);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
    }
}
