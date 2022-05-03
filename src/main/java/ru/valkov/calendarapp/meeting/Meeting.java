package ru.valkov.calendarapp.meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.valkov.calendarapp.user.User;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meeting_id_sequence"
    )
    @SequenceGenerator(
            name = "meeting_id_sequence",
            sequenceName = "meeting_id_sequence",
            allocationSize = 1
    )
    @Column(unique = true, nullable = false)
    private Long id;
    private String Name;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Enumerated(EnumType.STRING)
    private PeriodicityStatus status;
}
