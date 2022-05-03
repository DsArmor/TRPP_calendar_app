package ru.valkov.calendarapp.meeting;

import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;

import java.time.ZoneOffset;

@Component
public class MeetingMapper {

    public Meeting map(MeetingRequest request) {
        return Meeting.builder()
                .name(request.getName())
                .beginDateTime(request.getBeginDateTime().toLocalDateTime())
                .endDateTime(request.getEndDateTime().toLocalDateTime())
                .location(request.getDescription())
//                .owner(request.getOwnerId()) //todo add owner
                .build();
    }

    public MeetingResponse map(Meeting meeting) {
        return new MeetingResponse()
                .id(meeting.getId())
                .name(meeting.getName())
                .beginDateTime(meeting.getBeginDateTime().atOffset(ZoneOffset.UTC)) // todo it is right?
                .endDateTime(meeting.getEndDateTime().atOffset(ZoneOffset.UTC))
                .location(meeting.getLocation())
                .description(meeting.getDescription())
                .ownerId(meeting.getOwner().getId()); // todo think about
    }
}
