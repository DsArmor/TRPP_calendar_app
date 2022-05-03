package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.valkov.calendarapp.openapi.controller.MeetingApi;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.openapi.model.UserResponse;
import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrap;
import static ru.valkov.calendarapp.exceptions.ExceptionWrapper.wrapWithoutResult;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MeetingController implements MeetingApi {
    private final MeetingService meetingService;

    @Override
    public ResponseEntity<Object> createMeeting(MeetingRequest meetingRequest) {
        return wrap(meetingService::createMeeting, meetingRequest);
//        return ResponseEntity.ok(meetingService.createMeeting(meetingRequest));
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetings() {
        return wrap(meetingService::getMeetings);
//        return ResponseEntity.ok(meetingService.getMeetings());
    }
}
