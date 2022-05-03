package ru.valkov.calendarapp.meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.valkov.calendarapp.invite.Invite;
import ru.valkov.calendarapp.user.User;

import javax.persistence.*;
import javax.swing.text.html.InlineView;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            allocationSize = 1 //what?
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

    //bidirectional to invite
//    @OneToMany(
//            mappedBy = "meeting",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Invite> invites = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PeriodicityStatus status;
}
