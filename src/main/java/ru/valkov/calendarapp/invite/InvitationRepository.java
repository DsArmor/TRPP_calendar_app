package ru.valkov.calendarapp.invite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query("SELECT i FROM Invitation i WHERE i.invitedUser.id = ?1 AND i.meeting.id = ?2")
    List<Invitation> findByMeetingIdAndUserId(Long userId, Long meetingId);
}
