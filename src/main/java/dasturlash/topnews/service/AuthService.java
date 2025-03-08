package dasturlash.topnews.service;

import dasturlash.topnews.dto.StandardResponse;
import dasturlash.topnews.dto.profile.RegistrationDTO;
import dasturlash.topnews.entity.Profile;
import dasturlash.topnews.enums.AppLanguage;
import dasturlash.topnews.enums.ProfileStatus;
import dasturlash.topnews.service.emailService.EmailSendingService;
import dasturlash.topnews.service.emailService.EmailSentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmailSendingService  emailSendingService;
    private final EmailSentHistoryService emailSentHistoryService;
    private final ResourceBundleService resourceBundleService;
    private final ProfileService profileService;

    @Autowired
    public AuthService(EmailSendingService emailSendingService,
                       EmailSentHistoryService emailSentHistoryService,
                       ResourceBundleService resourceBundleService,
                       ProfileService profileService) {
        this.emailSendingService = emailSendingService;
        this.emailSentHistoryService = emailSentHistoryService;
        this.resourceBundleService = resourceBundleService;
        this.profileService = profileService;
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




}
