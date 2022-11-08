package ru.practicum.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.follows.Subscription;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(length = 50, unique = true)
    private String email;
    @Column(name = "is_followed")
    private boolean isFollowed;
    @OneToMany(mappedBy = "subscriber")
    private List<Subscription> subscriptions;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isFollowed=" + isFollowed +
                ", followedIds=" + subscriptions
                .stream()
                .map(subscription -> subscription.getFollowed().getId())
                .collect(Collectors.toList()) +
                '}';
    }
}
