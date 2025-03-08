package dasturlash.topnews.service;

import dasturlash.topnews.entity.ProfileRoleEntity;
import dasturlash.topnews.enums.Role;
import dasturlash.topnews.repository.ProfileRoleEntityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileRoleEntityService {
    private final ProfileRoleEntityRepository profileRoleEntityRepository;

    public ProfileRoleEntityService(ProfileRoleEntityRepository profileRoleEntityRepository) {
        this.profileRoleEntityRepository = profileRoleEntityRepository;
    }

    public void create(Integer profileId, Role role) {
        ProfileRoleEntity profileRoleEntity = new ProfileRoleEntity();
        profileRoleEntity.setProfileId(profileId);
        profileRoleEntity.setRole(role);
        profileRoleEntity.setCreatedDate(LocalDateTime.now());
        profileRoleEntityRepository.save(profileRoleEntity);
    }
}
