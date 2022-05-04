package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.openapi.model.PeriodicityResponse;
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
                .periodicity(mapPeriodicity(request))
                .build();
    }

    public MeetingResponse map(Meeting meeting) {
        return new MeetingResponse()
                .id(meeting.getId())
                .name(meeting.getName())
                .beginDateTime(meeting.getBeginDateTime().atOffset(zone))
                .endDateTime(meeting.getEndDateTime().atOffset(zone))
                .location(meeting.getLocation())
                .description(meeting.getDescription())
                .periodicity(mapPeriodicity(meeting))
                .owner(userMapper.map(meeting.getOwner()));
    }

    public Meeting map(MeetingResponse meetingResponse, User user) {
        return Meeting.builder()
                .id(meetingResponse.getId())
                .name(meetingResponse.getName())
                .beginDateTime(meetingResponse.getBeginDateTime().toLocalDateTime())
                .endDateTime(meetingResponse.getEndDateTime().toLocalDateTime())
                .location(meetingResponse.getLocation())
                .description(meetingResponse.getDescription())
                .owner(user)
                .periodicity(mapPeriodicity(meetingResponse))
                .build();
    }

    public Periodicity mapPeriodicity(MeetingResponse meetingResponse) {
        return Periodicity.valueOf(meetingResponse.getPeriodicity().getValue());
    }

    public Periodicity mapPeriodicity(MeetingRequest meetingRequest) {
        return Periodicity.valueOf(meetingRequest.getPeriodicity().getValue());
    }

    public PeriodicityResponse mapPeriodicity(Meeting meeting) {
        return PeriodicityResponse.valueOf(meeting.getPeriodicity().name());
    }
}
