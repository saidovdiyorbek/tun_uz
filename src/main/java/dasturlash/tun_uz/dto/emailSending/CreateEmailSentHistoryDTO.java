package dasturlash.tun_uz.dto.emailSending;

import javax.validation.constraints.Email;

public record CreateEmailSentHistoryDTO(
        @Email
        String toEmail,
        int code
) {
}
