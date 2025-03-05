package MJLee.onlineCourseService.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    String username;
    String email;
    String password;
    LocalDateTime createdAt;
    String newPassword;

    @Override
    public String toString() {
        return "UserDto{" +
            "username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}
