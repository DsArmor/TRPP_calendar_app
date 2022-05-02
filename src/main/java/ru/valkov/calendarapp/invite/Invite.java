package ru.valkov.calendarapp.invite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.meeting.PeriodicityStatus;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "invite")
public class Invite {
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Meeting meeting;

    // One to Many
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "user_id")
//    private List<User> users = new ArrayList<>();
    // bidirectional to user
    @OneToMany(
            mappedBy = "invite",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<User> users = new ArrayList<>();
    // end

    @Enumerated(EnumType.STRING)
    private MeetingDecisionStatus mdStatus;
//    @Enumerated(EnumType.STRING)
//    private UserOnMeetingStatus umStatus;
    // UserStatus for this?
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}