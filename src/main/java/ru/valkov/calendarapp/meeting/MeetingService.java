package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.exceptions.NotFoundException;
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

    public Long createMeeting(MeetingRequest meetingRequest) {
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

    public void deleteById(Long meetingId) {
        meetingRepository.deleteById(meetingId);
    }

    public MeetingResponse getById(Long meetingId) {
        return meetingRepository
                .findById(meetingId)
                .map(meetingMapper::map)
                .orElseThrow(() -> new NotFoundException("Meeting not found"));
    }

    public void updateById(Long meetingId, MeetingRequest meetingRequest) { // todo запретить изменение ownerId / надо ли менять тело MeetingRequest в котором ownerId поля больше нет?
        User owner = userMapper.map(getById(meetingId).getOwner());
        Meeting updatedMeeting = meetingMapper.map(meetingRequest, owner);
        // метод update нуждается в доработке согласно таске об оповещении юзеров измененении времени встречи
        updatedMeeting.setId(meetingId);
        meetingRepository.save(updatedMeeting);
    }

}
