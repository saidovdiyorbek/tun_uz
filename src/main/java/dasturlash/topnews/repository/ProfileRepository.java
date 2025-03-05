package dasturlash.topnews.repository;

import dasturlash.topnews.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findByEmailAndVisibleTrue(String username);
}
