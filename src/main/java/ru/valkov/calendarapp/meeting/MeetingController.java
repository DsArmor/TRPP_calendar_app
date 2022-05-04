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
    }

    @Override
    public ResponseEntity<List<MeetingResponse>> getMeetings() {
        return wrap(meetingService::getMeetings);
    }

    @Override
    public ResponseEntity<Void> deleteMeetingById(Long meetingId) {
        return wrapWithoutResult(meetingService::deleteById, meetingId);
    }

    @Override
    public ResponseEntity<MeetingResponse> getMeetingById(Long meetingId) {
        return wrap(meetingService::getById, meetingId);
    }

    @Override
    public ResponseEntity<Void> updateMeeting(Long meetingId, MeetingRequest meetingRequest) {
        return wrapWithoutResult(meetingService::updateById, meetingId, meetingRequest); // можно ли сделать функцию wrap принимающей n-ое количество аргументов
    }
}
