package ru.valkov.calendarapp.meeting;

import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;

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

    public


}
