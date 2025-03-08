package dasturlash.topnews.service.emailService;

import dasturlash.topnews.dto.StandardResponse;
import dasturlash.topnews.dto.emailSending.CreateEmailSentHistoryDTO;
import dasturlash.topnews.entity.EmailSentHistory;
import dasturlash.topnews.repository.EmailSentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailSentHistoryService {
    @Value("${code.expired.time}")
    private int expiredTime;
    private final EmailSentHistoryRepository repository;

    @Autowired
    public EmailSentHistoryService(EmailSentHistoryRepository repository) {
        this.repository = repository;
    }

    public void create(CreateEmailSentHistoryDTO dto) {
        boolean exists = repository.existsByEmailAndCodeAndExpiredTrue(dto.toEmail(), dto.code());
        if(exists){
            create(dto);
        }

        EmailSentHistory emailSentHistory = new EmailSentHistory();
        emailSentHistory.setEmail(dto.toEmail());
        emailSentHistory.setCode(dto.code());

        //def
        emailSentHistory.setSentDate(LocalDateTime.now());
        emailSentHistory.setExpiredDate(LocalDateTime.now().plusMinutes(expiredTime));
        emailSentHistory.setExpired(false);
        repository.save(emailSentHistory);
    }


    public StandardResponse checkCode(int code){
        EmailSentHistory byCode = repository.findByCode(code);
        if(byCode != null && byCode.getExpiredDate().isAfter(LocalDateTime.now())){
            byCode.setExpired(true);
            return new StandardResponse(byCode.getEmail(), true, byCode);
        }
        return new StandardResponse("Code expired", false, byCode);
    }

    //user register qilgan code jonatilgan, lekin u code expired bolgan, yana shu email bilan registerga kelsa
    public StandardResponse checkCode(String email){
        EmailSentHistory byCode = repository.findByEmail(email);
        //code hali expired bolmagan, shu uchun shunchaki xabar beramiz code jonatmaymiz
        if(byCode != null && byCode.getExpiredDate().isAfter(LocalDateTime.now())){
            byCode.setExpired(true);
            return new StandardResponse(byCode.getEmail(), true, null);
        }
        return new StandardResponse("Code expired", false, byCode);
    }

    public boolean checkAndChangeExpire(String email){
        EmailSentHistory byEmail = repository.findByEmail(email);
        if(byEmail != null && byEmail.getExpiredDate().isBefore(LocalDateTime.now())){
            byEmail.setExpired(true);
            repository.save(byEmail);
            //code expired bolgan
            return true;
        }
        //Code hali expired bolmagan
        return false;
    }
}
