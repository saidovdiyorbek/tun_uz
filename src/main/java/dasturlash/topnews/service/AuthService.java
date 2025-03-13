package dasturlash.topnews.service;

import dasturlash.topnews.config.CustomUserDetails;
import dasturlash.topnews.dto.PhotoResponse;
import dasturlash.topnews.dto.StandardResponse;
import dasturlash.topnews.dto.profile.RegistrationDTO;
import dasturlash.topnews.entity.Profile;
import dasturlash.topnews.enums.AppLanguage;
import dasturlash.topnews.enums.ProfileStatus;
import dasturlash.topnews.exceptions.AppBadException;
import dasturlash.topnews.service.emailService.EmailSendingService;
import dasturlash.topnews.service.emailService.EmailSentHistoryService;
import dasturlash.topnews.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final EmailSendingService  emailSendingService;
    private final EmailSentHistoryService emailSentHistoryService;
    private final ResourceBundleService resourceBundleService;
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;
    private final ProfileRoleEntityService  profileRoleEntityService;
    private final AttachService  attachService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(EmailSendingService emailSendingService,
                       EmailSentHistoryService emailSentHistoryService,
                       ResourceBundleService resourceBundleService,
                       ProfileService profileService,
                       AuthenticationManager authenticationManager,
                       ProfileRoleEntityService profileRoleEntityService,
                       AttachService attachService,
                       JwtUtil jwtUtil) {
        this.emailSendingService = emailSendingService;
        this.emailSentHistoryService = emailSentHistoryService;
        this.resourceBundleService = resourceBundleService;
        this.profileService = profileService;
        this.authenticationManager = authenticationManager;
        this.profileRoleEntityService = profileRoleEntityService;
        this.attachService = attachService;
        this.jwtUtil = jwtUtil;
    }

    public String registration(RegistrationDTO dto, AppLanguage lang) {
        ProfileStatus profileStatus = profileService.checkInRegistration(dto.username());

        if (profileStatus != null && profileStatus.equals(ProfileStatus.IN_REGISTRATION)) {
            //code tekshirilayapti expiredi, muddati otmagan bolsa shunchaki xabar jonatiladi
            boolean exists = emailSentHistoryService.checkAndChangeExpire(dto.username());
            if (!exists){
                return resourceBundleService.getMessage("already.registration", lang);
            }
            //true qaytadi code expired bolgan qayta code jonatvoramiz
            emailSendingService.sendRegistrationEmail(dto.username(), lang);
            return resourceBundleService.getMessage("sent.email", lang);
        }

        if (profileStatus == ProfileStatus.ACTIVE) {
            return resourceBundleService.getMessage("wrong.email", lang);
        }
        profileService.createNewProfileNotExists(dto);
        emailSendingService.sendRegistrationEmail(dto.username(), lang);
        return resourceBundleService.getMessage("sent.email", lang);
    }

    public String verification(int code/*, AppLanguage lang*/) {
        StandardResponse standardResponse = emailSentHistoryService.checkCode(code);
        if (!standardResponse.status()){
            return standardResponse.message();
        }
        Profile profile = profileService.getProfileByUsername(standardResponse.message());
        profileService.updateProfileStatus(profile);
        return "Verification successful";
    }


    public RegistrationDTO.LoginDTO.LoginResponse login(RegistrationDTO.LoginDTO dto, AppLanguage lang) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
            if (authentication.isAuthenticated()) {
                CustomUserDetails profile = (CustomUserDetails) authentication.getPrincipal();
                String token = jwtUtil.encode(profile.getUsername(), profile.getId(), profile.getRole());

                List<String> profileRoles = profileRoleEntityService.getRolesByProfileId(profile.getId());
                Profile currentUser = profileService.getById(profile.getId());

                attachService.openUrl(currentUser.getPhotoId());
                PhotoResponse photoResponse = new PhotoResponse(currentUser.getPhotoId(), attachService.openUrl(currentUser.getPhotoId()));

                return new RegistrationDTO.LoginDTO.LoginResponse(profile.getName(), currentUser.getSurname(), profile.getUsername(), profileRoles, photoResponse,token);
            }
            throw new AppBadException("Invalid email or password");
        }catch (BadCredentialsException e){
            throw new AppBadException("Invalid email or password");
        }catch (Exception e){
            e.printStackTrace();
            throw new AppBadException("Internal server error");
        }
    }
}
