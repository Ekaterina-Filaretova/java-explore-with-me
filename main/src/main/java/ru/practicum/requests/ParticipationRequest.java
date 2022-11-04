package ru.practicum.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.events.Event;
import ru.practicum.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "requester_id")
    private User requester;
    @Column(name = "created_on")
    private LocalDateTime created;
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Override
    public String toString() {
        return "ParticipationRequest{" +
                "id=" + id +
                ", event=" + event +
                ", requester=" + requester +
                ", created=" + created +
                ", status=" + status +
                '}';
    }
}
