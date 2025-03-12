package dasturlash.topnews.repository;

import dasturlash.topnews.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findByEmailAndVisibleTrue(String username);
    Optional<Profile> findByIdAndVisibleTrue(Integer profileId);
}
