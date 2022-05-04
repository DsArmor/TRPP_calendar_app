package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.exceptions.BadRequestException;
import ru.valkov.calendarapp.exceptions.NotFoundException;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;
import ru.valkov.calendarapp.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final UserService userService;
    private final UserMapper userMapper;

    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    public Long createMeeting(Long usersId, MeetingRequest meetingRequest) {
        validateMeetingTime(meetingRequest.getBeginDateTime().toLocalDateTime(),
                meetingRequest.getEndDateTime().toLocalDateTime());
        User user = userMapper.map(userService.getById(usersId));
        System.out.println(user);
        Meeting meeting = meetingMapper.map(meetingRequest, user);
        System.out.println(meeting);
        meetingRepository.save(meeting);
        return meeting.getId();
    }

    public List<MeetingResponse> getMeetings(Long usersId) { // todo возвращать только встречи для конкретного пользователя
        return meetingRepository
                .findAllJoinUserId(usersId)
                .stream()
                .map(meetingMapper::map)
                .collect(Collectors.toList());
    }

    public void deleteById(Long usersId, Long meetingId) { // todo удалять только встречу конкретного юзера
        meetingRepository.deleteById(meetingId);
    }

    public MeetingResponse getById(Long usersId, Long meetingId) {
        return meetingMapper
                .map(meetingRepository.findByIdJoinUserId(usersId, meetingId));
//                .orElseThrow(() -> new NotFoundException("Meeting not found"));
    }

    public void updateById(Long usersId, Long meetingId, MeetingRequest meetingRequest) {
        validateMeetingTime(meetingRequest.getBeginDateTime().toLocalDateTime(),
                meetingRequest.getEndDateTime().toLocalDateTime());
        User owner = userMapper.map(getById(usersId, meetingId).getOwner());
        Meeting updatedMeeting = meetingMapper.map(meetingRequest, owner);
        // метод update нуждается в доработке согласно таске об оповещении юзеров измененении времени встречи
        updatedMeeting.setId(meetingId);
        meetingRepository.save(updatedMeeting);
    }

    private void validateMeetingTime(LocalDateTime begin, LocalDateTime end) throws BadRequestException {
        if (begin.compareTo(end) > 0) {
            throw new BadRequestException("We can't go back to the past");
        }
    }

}
