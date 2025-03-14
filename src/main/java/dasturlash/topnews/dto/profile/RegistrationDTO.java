package dasturlash.topnews.dto.profile;

import dasturlash.topnews.dto.PhotoResponse;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public record RegistrationDTO(
        String name,
        String surname,
        String username,
        @Size(min = 8, max = 50)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$&_]).{8,}$",
                message = "The password must be at least 8 characters long and contain uppercase, lowercase, numbers, and special characters (@$&_)"
        )
        String password,
        String photoId

) {
        public record LoginDTO(
                @Email(message = "Invalid email address")
                String email,
                @NotBlank
                String password
        ) {
                public record LoginResponse(
                        String name,
                        String surname,
                        String username,
                        List<String> roles,
                        PhotoResponse infoPhoto,
                        String jwtToken
                ){}
        }
}
