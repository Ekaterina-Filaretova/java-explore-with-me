package ru.practicum.follows;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.follows.dto.SubscriptionDto;
import ru.practicum.users.dto.UserDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}")
@AllArgsConstructor
@Validated
public class PrivateSubscriptionController {

    private final PrivateSubscriptionService subscriptionService;

    @PostMapping(path = "/subscriptions")
    public SubscriptionDto addSubscription(@PathVariable Long userId,
                                           @RequestParam(name = "followedId") Long followedId) {
        return subscriptionService.addSubscription(userId, followedId);
    }

    @DeleteMapping(path = "/subscriptions/{subscriptionId}")
    public void deleteSubscription(@PathVariable Long userId,
                                   @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
    }

    @PatchMapping(path = "/follow")
    public UserDto makeFollowed(@PathVariable Long userId) {
        return subscriptionService.changeFollowed(userId, true);
    }

    @PatchMapping(path = "/unfollow")
    public UserDto makeUnfollowed(@PathVariable Long userId) {
        return subscriptionService.changeFollowed(userId, false);
    }

    @GetMapping(path = "/subscriptions/{subscriptionId}/events")
    public List<EventFullDto> getFollowedEvents(@PathVariable Long userId,
                                                @PathVariable Long subscriptionId,
                                                @PositiveOrZero @RequestParam(value = "from", defaultValue = "0")
                                                Integer from,
                                                @Positive @RequestParam(value = "size", defaultValue = "10")
                                                Integer size,
                                                @RequestParam(value = "sort", defaultValue = "DESC")
                                                FollowedEventsSort sort) {
        return subscriptionService.getFollowedEvents(userId, subscriptionId, from, size, sort);
    }

    @GetMapping(path = "/subscriptions")
    public List<SubscriptionDto> getSubscriptions(@PathVariable Long userId) {
        return subscriptionService.getSubscriptions(userId);
    }
}
