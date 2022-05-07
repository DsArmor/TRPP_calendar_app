package ru.valkov.calendarapp.invite;

import lombok.*;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserStatus;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "invitation_id_sequence"
    )
    @SequenceGenerator(
            name = "invitation_id_sequence",
            sequenceName = "invitation_id_sequence",
            allocationSize = 1
    )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User invitedUser;
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatusOnMeeting;
}
