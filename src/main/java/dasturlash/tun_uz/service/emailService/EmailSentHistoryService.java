package dasturlash.tun_uz.service.emailService;

import dasturlash.tun_uz.dto.emailSending.CreateEmailSentHistoryDTO;
import dasturlash.tun_uz.entity.EmailSentHistory;
import dasturlash.tun_uz.repository.EmailSentHistoryRepository;
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
}
