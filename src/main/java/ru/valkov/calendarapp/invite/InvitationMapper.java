package ru.valkov.calendarapp.invite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.meeting.MeetingMapper;
import ru.valkov.calendarapp.openapi.model.*;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvitationMapper {
    private final UserMapper userMapper;
    private final MeetingMapper meetingMapper;

    public Invitation map(UserResponse invitedUser, List<MeetingResponse> meetings) {
        User user = userMapper.map(invitedUser);
        return Invitation.builder()
                .invitedUser(user)
                .meetings(meetingMapper.mapAll(meetings, user))
                .invitationStatus(InvitationStatus.NEW)
                .build();
    }

    public InviteResponse map(Invitation invitation) {
        return new InviteResponse()
                .invitedUser(userMapper.map(invitation.getInvitedUser()))
                .meetings(meetingMapper.mapAll(invitation.getMeetings()))
                .invitationStatus(mapInvitationStatus(invitation.getInvitationStatus()));
    }
    public InvitationStatusResponse mapInvitationStatus(InvitationStatus status) {
        return InvitationStatusResponse.valueOf(status.name());
    }

    public InvitationStatus mapInvitationStatus(InvitationStatusResponse status) {
        return InvitationStatus.valueOf(status.name());
    }
}
