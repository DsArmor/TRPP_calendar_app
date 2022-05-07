package ru.valkov.calendarapp.invite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.exceptions.NotFoundException;
import ru.valkov.calendarapp.meeting.MeetingService;
import ru.valkov.calendarapp.openapi.model.*;
import ru.valkov.calendarapp.user.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    private final UserService userService;
    private final MeetingService meetingService;

    public Long createInvitation(Long ownerId, String groupId, InviteRequest inviteRequest) {
        List<MeetingResponse> meetingResponses = meetingService.getByGroupIdAndOwnerId(ownerId, groupId);
        UserResponse invitedUser = userService.getById(inviteRequest.getInvitedUserId());
        Invitation invitation = invitationMapper.map(invitedUser, meetingResponses);
        return invitationRepository.save(invitation).getId();
    }

    public InviteResponse getByUserIdAndMeetingId(Long userId, String meetingGroupId) {
        return invitationRepository
                .findByMeetingGroupIdAndUserId(userId, meetingGroupId)
                .stream()
                .map(invitationMapper::map)
                .findAny()
                .orElseThrow(() -> new NotFoundException("Invitation not found"));
    }

    @Transactional
    public void updateInvitationByUserIdAndMeetingId(Long userId, String meetingGroupId, InvitationStatusResponse invitationStatus) {
        InvitationStatus status = invitationMapper.mapInvitationStatus(invitationStatus);
        Optional<Invitation> invitation = invitationRepository
                .findByMeetingGroupIdAndUserId(userId, meetingGroupId)
                .stream()
                .findAny();
        if (invitation.isPresent()) {
            invitation.get().setInvitationStatus(status);
        } else {
            throw new NotFoundException("Invitation not found");
        }
    }
}
