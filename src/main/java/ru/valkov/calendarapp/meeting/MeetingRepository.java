package ru.valkov.calendarapp.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query(value = """
                SELECT m FROM Meeting m
                LEFT JOIN Invitation i ON m.id = i.meeting.id
                WHERE (i.invitedUser.id = :userId AND i.invitationStatus != ru.valkov.calendarapp.invite.InvitationStatus.REJECTED)
                       OR m.owner.id = :userId
            """)
    List<Meeting> findAllByUserId(@Param("userId") Long userId);

    @Query(value = """
                SELECT m FROM Meeting m
                LEFT JOIN Invitation i ON m.id = i.meeting.id
                WHERE ((i.invitedUser.id = :userId AND i.invitationStatus != ru.valkov.calendarapp.invite.InvitationStatus.REJECTED)
                       OR m.owner.id = :userId) AND (m.beginDateTime >= :beginDateTime AND m.endDateTime <= :endDateTime)
            """)
    List<Meeting> findAllByStartTimeAndEndTime(Long userId, LocalDateTime beginDateTime, LocalDateTime endDateTime);

    @Query("select m from Meeting m where m.owner.id = :oId and m.id = :mId")
    Meeting findByIdAndOwnerId(@Param("oId") Long userId, @Param("mId") Long meetingId);
}
