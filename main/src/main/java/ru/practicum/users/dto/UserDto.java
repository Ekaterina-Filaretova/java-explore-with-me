package ru.practicum.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.subscriptions.dto.SubscriptionDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty(message = "Передано пустое имя пользователя")
    @NotEmpty(message = "Передано пустое имя пользователя")
    private String name;
    @Email(message = "Не верный формат почты")
    private String email;
    private boolean isFollowed;
    private List<SubscriptionDto> subscriptions;

}
