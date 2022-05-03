package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.openapi.model.UserResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;
import ru.valkov.calendarapp.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final UserService userService;
    private final UserMapper userMapper;

    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    public Long createMeeting(MeetingRequest meetingRequest) { // todo exception
        System.out.println(meetingRequest.getBeginDateTime());
        User user = userMapper.map(userService.getById(meetingRequest.getOwnerId()));
        Meeting meeting = meetingMapper.map(meetingRequest, user);
        meetingRepository.save(meeting);
        return meeting.getId();
    }

    public List<MeetingResponse> getMeetings() {
        return meetingRepository
                .findAll()
                .stream()
                .map(meetingMapper::map)
                .collect(Collectors.toList());
    }

}
