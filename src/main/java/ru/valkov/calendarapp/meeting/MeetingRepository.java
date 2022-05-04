package ru.valkov.calendarapp.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.valkov.calendarapp.openapi.model.MeetingResponse;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("select m from Meeting m where m.owner.id = :id")
    List<Meeting> findAllJoinUserId(@Param("id") Long id);

    @Query("select m from Meeting m where m.owner.id = :oId and m.id = :mId")
    Meeting findByIdJoinUserId(@Param("oId") Long userId, @Param("mId") Long meetingId);
}
