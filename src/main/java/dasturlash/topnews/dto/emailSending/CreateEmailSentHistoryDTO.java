package dasturlash.topnews.dto.emailSending;

import javax.validation.constraints.Email;

public record CreateEmailSentHistoryDTO(
        @Email
        String toEmail,
        int code
) {
}
