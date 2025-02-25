package dasturlash.tun_uz.repository;

import dasturlash.tun_uz.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findByEmailAndVisibleTrue(String username);
}
