package ru.practicum.subscriptions;

import lombok.*;
import ru.practicum.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private User subscriber;
    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;
    @Column(name = "subscribe_date")
    private LocalDateTime subscribeDate;

}
