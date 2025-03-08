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
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "        a {\n" +
                "            padding: 10px 30px;\n" +
                "            display: inline-block;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        .button-link {\n" +
                "            text-decoration: none;\n" +
                "            color: white;\n" +
                "            background-color: indianred;\n" +
                "        }\n" +
                "\n" +
                "        .button-link:hover {\n" +
                "            background-color: #dd4444;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Complete Registration</h1>\n" +
                "<p>\n" +
                "    Please click to button link for completing registration:<a class=\"button-link\"\n" +
                "        href=\"http://localhost:"+serverPort+"/api/auth/verification/%d\" target=\"_blank\">Click there</a>\n" +
                "</p>\n" +
                "</body>\n" +
                "</html>";
        body = String.format(body, randomCode);
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
