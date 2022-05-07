package ru.valkov.calendarapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.meeting.MeetingService;
import ru.valkov.calendarapp.openapi.controller.UsersApi;
import ru.valkov.calendarapp.openapi.model.*;
import ru.valkov.calendarapp.invite.InvitationService;

import java.time.OffsetDateTime;
import java.util.List;

import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrap;
import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrapWithoutResult;

@RequiredArgsConstructor
@Controller
public class UserController implements UsersApi {
    private final UserService userService;
    private final MeetingService meetingService;
    private final InvitationService invitationService;

    @Override
    public ResponseEntity<Object> createUser(UserRequest userRequest) {
        return wrap(userService::createUser, userRequest);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        return wrap(userService::getUsers);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Long userId) {
        return wrapWithoutResult(userService::deleteById, userId);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long userId) {
        return wrap(userService::getById, userId);
    }

    @Override
    public ResponseEntity<Void> updateUser(Long userId, UserRequest userRequest) {
        return wrapWithoutResult(userService::updateById, userId, userRequest);
    }

    @Override
    public ResponseEntity<Object> createMeeting(Long usersId, MeetingRequest meetingRequest) {
        return wrap(meetingService::createMeeting, usersId, meetingRequest);
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetings(Long usersId) {
        return wrap(meetingService::getMeetings, usersId);
    }

    @Override
    public ResponseEntity<Void> deleteMeetingById(Long usersId, Long meetingId) {
        return wrapWithoutResult(meetingService::deleteById, usersId,  meetingId);
    }

    @Override
    public ResponseEntity<MeetingResponse> getMeetingById(Long usersId, Long meetingId) {
        return wrap(meetingService::getByIdAndOwnerId, usersId,  meetingId);
    }

    @Override
    public ResponseEntity<Void> updateMeeting(Long usersId, Long meetingId, MeetingRequest meetingRequest) {
        return wrapWithoutResult(meetingService::updateById, usersId, meetingId, meetingRequest);
    }
    @Override
    public ResponseEntity<Object> createInvitation(Long userId, String meetingGroupId, InviteRequest inviteRequest) {
        return wrap(invitationService::createInvitation, userId, meetingGroupId, inviteRequest);
    }

    @Override
    public ResponseEntity<InviteResponse> getInvitationByUserIdAndMeetingId(Long userId, String meetingGroupId) {
        return wrap(invitationService::getByUserIdAndMeetingId, userId, meetingGroupId);
    }

    @Override
    public ResponseEntity<Void> updateInvitationByUserIdAndMeetingId(Long userId, String meetingGroupId, InvitationStatusResponse invitationStatus) {
        return wrapWithoutResult(invitationService::updateInvitationByUserIdAndMeetingId, userId, meetingGroupId, invitationStatus);
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetingsByStartTimeAndEndTime(Long userId, OffsetDateTime from, OffsetDateTime to) {
        return wrap(meetingService::getMeetingsByStartTimeAndEndTime, userId, from, to);
    }
}
