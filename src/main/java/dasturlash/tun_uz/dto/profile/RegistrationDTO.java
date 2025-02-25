package dasturlash.tun_uz.dto.profile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record RegistrationDTO(
        String name,
        String surname,
        String username,
        @Size(min = 8, max = 50)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$&_]).{8,}$",
                message = "The password must be at least 8 characters long and contain uppercase, lowercase, numbers, and special characters (@$&_)"
        )
        String password

) {
}
