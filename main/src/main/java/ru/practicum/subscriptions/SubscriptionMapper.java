package ru.practicum.subscriptions;

import org.springframework.stereotype.Component;
import ru.practicum.subscriptions.dto.SubscriptionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriptionMapper {

    public SubscriptionDto convertToDto(Subscription subscription) {
        return new SubscriptionDto(subscription.getFollowed().getName(),
                parseDate(subscription.getSubscribeDate()));
    }

    public List<SubscriptionDto> convertToDto(List<Subscription> subscriptions) {
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            subscriptionDtos.add(convertToDto(subscription));
        }
        return subscriptionDtos;
    }

    private String parseDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
