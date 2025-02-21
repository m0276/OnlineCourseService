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
}
