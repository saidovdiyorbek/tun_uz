package dasturlash.tun_uz.repository;

import dasturlash.tun_uz.entity.EmailSentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailSentHistoryRepository extends JpaRepository<EmailSentHistory, String> {

    boolean existsByEmailAndCodeAndExpiredTrue(String email, int code);

}
