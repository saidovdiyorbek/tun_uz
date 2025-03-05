package dasturlash.topnews.service;

import dasturlash.topnews.dto.profile.RegistrationDTO;
import dasturlash.topnews.entity.Profile;
import dasturlash.topnews.enums.ProfileStatus;
import dasturlash.topnews.enums.Role;
import dasturlash.topnews.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRoleEntityService profileRoleEntityService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ProfileRoleEntityService profileRoleEntityService) {
        this.profileRepository = profileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRoleEntityService = profileRoleEntityService;
    }

    public Profile createNewProfileNotExists(RegistrationDTO dto){
        Profile profile = new Profile();
        profile.setName(dto.name());
        profile.setSurname(dto.surname());
        profile.setEmail(dto.username());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.password()));
        profile.setPhotoId(dto.photoId());

        //def
        profile.setStatus(ProfileStatus.IN_REGISTRATION);
        profile.setVisible(true);
        profile.setCreatedDate(LocalDateTime.now());
        profileRepository.save(profile);
        profileRoleEntityService.create(profile.getId(), Role.MODERATOR);
        return profile;
    }

    public Profile getProfileByUsername(String username) {
        Profile byEmailAndVisibleTrue = profileRepository.findByEmailAndVisibleTrue(username);
        if (byEmailAndVisibleTrue == null) {
            return null;
        }
        return byEmailAndVisibleTrue;
    }

    public void updateProfileStatus(Profile profile) {
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
    }

    public ProfileStatus checkInRegistration(String username){
        Profile inRegistrationProfile = profileRepository.findByEmailAndVisibleTrue(username);
        if(inRegistrationProfile != null && inRegistrationProfile.getStatus().equals(ProfileStatus.IN_REGISTRATION)){
            //oldin royxatdan otgan in_registration state da, qayta code jonatamiz
            return ProfileStatus.IN_REGISTRATION;
        }
        //endi royxatdan otayapdi
        if (inRegistrationProfile != null && inRegistrationProfile.getStatus().equals(ProfileStatus.ACTIVE)) {
            return ProfileStatus.ACTIVE;
        }
        return null;
    }
}
