package MJLee.onlineCourseService.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    String nickname;
    String email;
    String password;
    LocalDateTime createdAt;
    String newPassword;

    @Override
    public String toString() {
        return "UserDto{" +
            "nickname='" + nickname + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", createdAt=" + createdAt +
            ", newPassword='" + newPassword + '\'' +
            '}';
    }
}
