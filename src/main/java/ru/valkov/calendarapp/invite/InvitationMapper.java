package ru.valkov.calendarapp.invite;

import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.openapi.model.InviteRequest;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserStatus;

@Component
public class InvitationMapper {
    public Invitation map(InviteRequest inviteRequest, User invitedUser, Meeting meeting) {
        return Invitation.builder()
                .user(invitedUser)
                .meeting(meeting)
                .userStatusOnMeeting(mapUserStatus(inviteRequest))
                .invitationStatus(InvitationStatus.NEW)
                .build();
    }

    public UserStatus mapUserStatus(InviteRequest inviteRequest) {
        return UserStatus.valueOf(inviteRequest.getUserStatusOnMeeting().getValue());
    }
}
