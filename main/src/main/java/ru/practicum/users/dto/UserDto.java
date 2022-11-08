package ru.practicum.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.follows.dto.SubscriptionDto;

import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    @Email(message = "Не верный формат почты")
    private String email;
    private boolean isFollowed;
    private List<SubscriptionDto> subscriptions;

}
