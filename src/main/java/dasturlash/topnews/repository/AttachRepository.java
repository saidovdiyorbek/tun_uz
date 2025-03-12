package dasturlash.topnews.repository;

import dasturlash.topnews.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<Attach, String> {
    boolean existsByIdAndVisibleTrue(String attachId);
}
