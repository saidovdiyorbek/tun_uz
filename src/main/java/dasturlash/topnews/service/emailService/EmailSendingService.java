package dasturlash.topnews.service.emailService;

import dasturlash.topnews.dto.emailSending.CreateEmailSentHistoryDTO;
import dasturlash.topnews.enums.AppLanguage;
import dasturlash.topnews.util.random.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Value("${server.port}")
    private String serverPort;

    private final JavaMailSender mailSender;
    private final EmailSentHistoryService emailSentHistoryService;

    @Autowired
    public EmailSendingService(JavaMailSender mailSender,  EmailSentHistoryService emailSentHistoryService) {
        this.mailSender = mailSender;
        this.emailSentHistoryService = emailSentHistoryService;
    }

    public void sendRegistrationEmail(String email, AppLanguage lang) {
        String subject = "Registration Successfully!";
        String language = lang.name();
        int randomCode = RandomUtil.getRandomCode();
        String body = String.format("""
        <!DOCTYPE html>
        <html lang="%s">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="Content-Language" content="%s">
            <meta name="language" content="%s">
            <title>Title</title>
            <style>
                a {
                    padding: 10px 30px;
                    display: inline-block;
                }
                .button-link {
                    text-decoration: none;
                    color: white;
                    background-color: indianred;
                }
                .button-link:hover {
                    background-color: #dd4444;
                }
            </style>
        </head>
        <body>
            <h1>Complete Registration</h1>
            <p>
                Please click to button link for completing registration:
                <a class="button-link" href="http://"%s"/api/auth/verification/%d" target="_blank">
                    Click there
                </a>
            </p>
        </body>
        </html>
        """, language, language, language,serverPort,randomCode);
        sendMimeEmail(email, subject, body);
        //kimga qaysi code ketganligin yaratish
        CreateEmailSentHistoryDTO dto = new CreateEmailSentHistoryDTO(email, randomCode);
        emailSentHistoryService.create(dto);
    }



    private void sendMimeEmail(String email, String subject, String body) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(msg);

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

}
