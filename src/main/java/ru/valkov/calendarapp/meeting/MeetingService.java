package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    public Long createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingMapper.map(meetingRequest);
        meetingRepository.save(meeting);
        return meeting.getId();
    }

    public List<>

}
