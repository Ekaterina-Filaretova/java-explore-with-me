package ru.practicum.follows;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.Event;
import ru.practicum.events.EventMapper;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.privateaccess.PrivateEventService;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.follows.dto.SubscriptionDto;
import ru.practicum.users.AdminUserService;
import ru.practicum.users.User;
import ru.practicum.users.UserMapper;
import ru.practicum.users.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PrivateSubscriptionServiceImpl implements PrivateSubscriptionService {

    private final SubscriptionMapper subscriptionMapper;
    private final AdminUserService userService;
    private final PrivateEventService eventService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public SubscriptionDto addSubscription(Long userId, Long followedId) {
        if (userId.equals(followedId)) {
            throw new ValidationException("На самого себя подписаться невозможно");
        }
        User subscriber = userService.getUserById(userId);
        User user = userService.getUserById(followedId);
        if (!user.isFollowed()) {
            throw new ValidationException("На данного пользователя подписаться невозможно");
        }
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setFollowed(user);
        subscription.setSubscribeDate(LocalDateTime.now());
        subscriptionRepository.save(subscription);
        log.info("Пользователь {} подписался на {}", subscriber, user);
        return subscriptionMapper.convertToDto(subscription);
    }

    @Override
    public void deleteSubscription(Long userId, Long subscriptionId) {
        Subscription subscription = getFollowById(subscriptionId);
        checkFollower(userId, subscription);
        subscriptionRepository.delete(subscription);
        log.info("Удалена подписка {}", subscription);
    }

    @Override
    @Transactional
    public UserDto changeFollowed(Long userId, boolean isFollowed) {
        User user = userService.getUserById(userId);
        user.setFollowed(isFollowed);
        log.info("Пользователь {} изменил возможность подписываться на себя", user);
        return userMapper.convertToDto(user);
    }

    @Override
    public List<EventFullDto> getFollowedEvents(Long userId, Long subscriptionId, Integer from, Integer size,
                                                FollowedEventsSort sort) {
        Subscription subscription = getFollowById(subscriptionId);
        checkFollower(userId, subscription);
        Pageable pageable = PageRequest.of(from, size, getSort(sort));
        List<Event> events = eventService.findAllByInitiatorId(subscription.getFollowed().getId(), pageable);
        log.info("Найден список событий {}", events);
        return eventMapper.convertToDto(events);
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(Long userId) {
        userService.getUserById(userId);
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberId(userId);
        log.info("Найдены подписки {}", subscriptions);
        return subscriptionMapper.convertToDto(subscriptions);
    }

    private Subscription getFollowById(Long followId) {
        return subscriptionRepository.findById(followId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Подписка с id " + followId + " не найдена"));
    }

    private void checkFollower(Long userId, Subscription subscription) {
        User user = userService.getUserById(userId);
        if (!user.getId().equals(subscription.getSubscriber().getId())) {
            throw new ValidationException("Действие может выполнить только подписчик");
        }
    }

    private Sort getSort(FollowedEventsSort sort) {
        return Sort.by(Sort.Direction.valueOf(sort.name()), "eventDate");
    }
}
