package ru.valkov.calendarapp.invite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserStatus;
import javax.persistence.*;

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
            generator = "invite_id_sequence"
    )
    @SequenceGenerator(
            name = "invite_id_sequence",
            sequenceName = "invite_id_sequence",
            allocationSize = 1
    )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Enumerated(EnumType.STRING)
    private MeetingDecisionStatus mdStatus;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
