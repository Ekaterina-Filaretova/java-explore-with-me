package ru.practicum.subscriptions;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.subscriptions.dto.SubscriptionDto;
import ru.practicum.users.dto.UserDto;

import java.util.List;

public interface PrivateSubscriptionService {

    SubscriptionDto addSubscription(Long userId, Long followedId);

    void deleteSubscription(Long userId, Long subscriptionId);

    UserDto changeFollowed(Long userId, boolean isFollowed);

    List<EventFullDto> getFollowedEvents(Long userId, Long subscriptionId, Integer from, Integer size,
                                         FollowedEventsSort sort);

    List<SubscriptionDto> getSubscriptions(Long userId);
}
