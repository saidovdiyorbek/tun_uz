package dasturlash.topnews.service.emailService;

import dasturlash.topnews.dto.StandardResponse;
import dasturlash.topnews.dto.emailSending.CreateEmailSentHistoryDTO;
import dasturlash.topnews.entity.EmailSentHistory;
import dasturlash.topnews.repository.EmailSentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailSentHistoryService {
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
        emailSentHistory.setExpiredDate(LocalDateTime.now().plusMinutes(3));
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
}
