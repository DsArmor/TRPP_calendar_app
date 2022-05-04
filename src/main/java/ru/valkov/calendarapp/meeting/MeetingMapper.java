package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;

import java.time.ZoneOffset;

@RequiredArgsConstructor
@Component
public class MeetingMapper {

    private final UserMapper userMapper;
    private final static ZoneOffset zone = ZoneOffset.UTC;

    public Meeting map(MeetingRequest request, User user) {
        return Meeting.builder()
                .name(request.getName())
                .beginDateTime(request.getBeginDateTime().toLocalDateTime())
                .endDateTime(request.getEndDateTime().toLocalDateTime())
                .location(request.getLocation())
                .description(request.getDescription())
                .owner(user)
                .status(PeriodicityStatus.NONE)
                .build();
    }

    public MeetingResponse map(Meeting meeting) { // todo не возвращаем статус?
        return new MeetingResponse()
                .id(meeting.getId())
                .name(meeting.getName())
                .beginDateTime(meeting.getBeginDateTime().atOffset(zone))
                .endDateTime(meeting.getEndDateTime().atOffset(zone))
                .location(meeting.getLocation())
                .description(meeting.getDescription())
                .owner(userMapper.map(meeting.getOwner()));
    }

}
