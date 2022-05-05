package ru.valkov.calendarapp.invite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.meeting.MeetingMapper;
import ru.valkov.calendarapp.meeting.MeetingService;
import ru.valkov.calendarapp.openapi.model.InviteRequest;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;
import ru.valkov.calendarapp.user.UserService;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final MeetingService meetingService;
    private final MeetingMapper meetingMapper;

    public Long createInvitation(Long userId, Long meetingId, InviteRequest inviteRequest) {
        User invitedUser = userMapper.map(userService.getById(userId));
        Meeting meeting = meetingMapper.map(meetingService.getById(userId, meetingId), invitedUser);
        Invitation invitation = invitationMapper.map(inviteRequest, invitedUser, meeting);
        invitationRepository.save(invitation);
        return invitation.getId();
    }
}
