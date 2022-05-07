package ru.valkov.calendarapp.meeting;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.exceptions.BadRequestException;
import ru.valkov.calendarapp.openapi.model.MeetingRequest;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;
import ru.valkov.calendarapp.user.User;
import ru.valkov.calendarapp.user.UserMapper;
import ru.valkov.calendarapp.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    public String createMeeting(Long usersId, MeetingRequest meetingRequest) {
        validateMeetingTime(meetingRequest.getUntil(), meetingRequest.getBeginDateTime().toLocalDateTime(),
                meetingRequest.getEndDateTime().toLocalDateTime());
        User user = userMapper.map(userService.getById(usersId));
        Meeting meeting = meetingMapper.map(meetingRequest, user);
        saveAll(meeting);
        return meeting.getGroupId();
    }

    @SneakyThrows
    private void saveAll(Meeting meeting) {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting);
        if (meeting.getPeriodicity() != Periodicity.NONE) {
            Function<Integer, TemporalAmount> periodAdder = getPeriodAdder(meeting.getPeriodicity());
            LocalDateTime nextBeginDateTime = meeting.getBeginDateTime();
            LocalDateTime nextEndDateTime = meeting.getEndDateTime();
            while (canAddOneMoreMeeting(meeting, nextBeginDateTime)) {
                nextBeginDateTime = nextBeginDateTime.plus(periodAdder.apply(1));
                nextEndDateTime = nextEndDateTime.plus(periodAdder.apply(1));
                Meeting newMeeting = meeting.clone();
                newMeeting.setBeginDateTime(nextBeginDateTime);
                newMeeting.setEndDateTime(nextEndDateTime);
                meetings.add(newMeeting);
            }
        }
        meetingRepository.saveAll(meetings);
    }

    private boolean canAddOneMoreMeeting(Meeting meeting, LocalDateTime nextMeetingBeginDateTime) {
        return (meeting.getUntil().atStartOfDay().isAfter(nextMeetingBeginDateTime));
    }

    private Function<Integer, TemporalAmount> getPeriodAdder(Periodicity periodicity) {
        return switch (periodicity) {
            case DAY -> Period::ofDays;
            case WEEK -> Period::ofWeeks;
            case MONTH -> Period::ofMonths;
            case NONE -> throw new IllegalArgumentException();
        };
    }

    public List<MeetingResponse> getMeetings(Long usersId) {
        userService.getById(usersId);
        return meetingRepository
                .findAllByUserId(usersId)
                .stream()
                .map(meetingMapper::map)
                .collect(Collectors.toList());
    }

    public void deleteById(Long usersId, Long meetingId) { // todo удалять только встречу конкретного юзера, хотя она не зависит от usersId поэтому можно его не юзать?
        meetingRepository.deleteById(meetingId);
    }

    public MeetingResponse getByIdAndOwnerId(Long ownerId, Long meetingId) {
        return meetingMapper.map(meetingRepository.findByIdAndOwnerId(ownerId, meetingId));
    }

    public List<MeetingResponse> getByGroupIdAndOwnerId(Long ownerId, String groupId) {
        return meetingMapper.mapAll(meetingRepository.findAllByIdAndGroupId(ownerId, groupId));
    }

    public void updateById(Long usersId, Long meetingId, MeetingRequest meetingRequest) {
        validateMeetingTime(meetingRequest.getBeginDateTime().toLocalDateTime(), meetingRequest.getEndDateTime().toLocalDateTime());
        User owner = userMapper.map(userService.getById(usersId));
        Meeting updatedMeeting = meetingMapper.map(meetingRequest, owner);
        // метод update нуждается в доработке согласно таске об оповещении юзеров измененении времени встречи
        updatedMeeting.setId(meetingId);
        meetingRepository.save(updatedMeeting);
    }

    public List<MeetingResponse> getMeetingsByStartTimeAndEndTime(Long userId, OffsetDateTime beginTime, OffsetDateTime endTime) {
        validateMeetingTime(beginTime.toLocalDateTime(), endTime.toLocalDateTime());
        userService.getById(userId);
        return meetingRepository
                .findAllByStartTimeAndEndTime(userId, beginTime.toLocalDateTime(), endTime.toLocalDateTime())
                .stream()
                .map(meetingMapper::map)
                .collect(Collectors.toList());
    }

    private void validateMeetingTime(LocalDate until, LocalDateTime begin, LocalDateTime end) {
        if (until.atStartOfDay().isBefore(begin)) {
            throw new BadRequestException("The start time cannot be later until date");
        }
        validateMeetingTime(begin, end);
    }

    private void validateMeetingTime(LocalDateTime begin, LocalDateTime end) {
        if (begin.isAfter(end)) {
            throw new BadRequestException("The start time cannot be later than the end time");
        }
    }
}
