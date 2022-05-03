package ru.valkov.calendarapp.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;

import java.time.ZoneOffset;

@Component
public class MeetingMapper {

    @Autowired
    private UserMapper userMapper;

    private final static ZoneOffset zone = ZoneOffset.UTC;

    public Meeting map(MeetingRequest request, User user) {
        return Meeting.builder()
                .name(request.getName())
                .beginDateTime(request.getBeginDateTime().toLocalDateTime())
                .endDateTime(request.getEndDateTime().toLocalDateTime())
                .location(request.getDescription())
                .owner(user) //todo add owner
                .build();
    }

    public MeetingResponse map(Meeting meeting) {
        return new MeetingResponse()
                .id(meeting.getId())
                .name(meeting.getName())
                .beginDateTime(meeting.getBeginDateTime().atOffset(zone)) // todo it is right?
                .endDateTime(meeting.getEndDateTime().atOffset(zone))
                .location(meeting.getLocation())
                .description(meeting.getDescription())
                .owner(userMapper.map(meeting.getOwner()));
    }
}
