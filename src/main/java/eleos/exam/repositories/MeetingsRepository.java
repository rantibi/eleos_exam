package eleos.exam.repositories;

import eleos.exam.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingsRepository extends JpaRepository<Meeting, Long> {
}
