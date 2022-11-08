package ru.practicum.follows;

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
    @Column(name = "subscription_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subscriber_id")
    private User subscriber;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "followed_id")
    private User followed;
    @Column(name = "subscribe_date")
    private LocalDateTime subscribeDate;

}
