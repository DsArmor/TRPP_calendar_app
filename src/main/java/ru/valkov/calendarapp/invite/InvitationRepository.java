package ru.valkov.calendarapp.invite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query(
            value = """
            SELECT * FROM invitation
            JOIN invitation_meeting im ON invitation.id = im.invitation_id
            JOIN meeting ON im.meeting_id = meeting.id
            WHERE (meeting.owner_id = :userId OR (invitation.user_id = :userId AND invitation.invitation_status != 'REJECTED')) 
                AND meeting.group_id = :meetingGroupId
            """, nativeQuery = true
    )
    List<Invitation> findByMeetingGroupIdAndUserId(Long userId, String meetingGroupId);
}
