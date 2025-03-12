package dasturlash.topnews.repository;

import dasturlash.topnews.entity.ProfileRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRoleEntityRepository extends JpaRepository<ProfileRoleEntity, Integer> {
    @Query(value = "select STRING_AGG(pr.roles, ',') roles " +
            "from profile_role pr " +
            "where pr.profile_id = ?1 " +
            "group by pr.roles", nativeQuery = true)
    List<String> getRolesByProfileId(Integer profileId);
}
