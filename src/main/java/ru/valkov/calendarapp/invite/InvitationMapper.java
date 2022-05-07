package ru.valkov.calendarapp.invite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.meeting.MeetingMapper;
import ru.valkov.calendarapp.openapi.model.*;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;
import ru.valkov.calendarapp.user.UserStatus;

@Component
@RequiredArgsConstructor
public class InvitationMapper {
    private final UserMapper userMapper;
    private final MeetingMapper meetingMapper;

    public Invitation map(InviteRequest inviteRequest, UserResponse invitedUser, MeetingResponse meeting) {
        User user = userMapper.map(invitedUser);
        return Invitation.builder()
                .invitedUser(user)
                .meeting(meetingMapper.map(meeting, user))
                .userStatusOnMeeting(mapUserStatus(inviteRequest.getUserStatusOnMeeting()))
                .invitationStatus(InvitationStatus.NEW)
                .build();
    }

    public InviteResponse map(Invitation invitation) {
        return new InviteResponse()
                .invitedUser(userMapper.map(invitation.getInvitedUser()))
                .meeting(meetingMapper.map(invitation.getMeeting()))
                .userStatusOnMeeting(mapUserStatus(invitation.getUserStatusOnMeeting()))
                .invitationStatus(mapInvitationStatus(invitation.getInvitationStatus()));
    }

    public UserStatus mapUserStatus(UserStatusOnMeeting status) {
        return UserStatus.valueOf(status.getValue());
    }

    public UserStatusOnMeeting mapUserStatus(UserStatus status) {
        return UserStatusOnMeeting.valueOf(status.name());
    }

    public InvitationStatusResponse mapInvitationStatus(InvitationStatus status) {
        return InvitationStatusResponse.valueOf(status.name());
    }

    public InvitationStatus mapInvitationStatus(InvitationStatusResponse status) {
        return InvitationStatus.valueOf(status.name());
    }
}
