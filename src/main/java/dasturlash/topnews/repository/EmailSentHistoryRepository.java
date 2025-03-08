package dasturlash.topnews.repository;

import dasturlash.topnews.entity.EmailSentHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface EmailSentHistoryRepository extends JpaRepository<EmailSentHistory, String> {

    boolean existsByEmailAndCodeAndExpiredTrue(String email, int code);

    @Query(
            "from EmailSentHistory e " +
            "where e.code = ?1 and e.expired = false")
    EmailSentHistory findByCode(int code);

    @Query(
            "from EmailSentHistory e " +
            "where e.email = ?1 and e.expired = false")
    EmailSentHistory findByEmail(String email);

}
