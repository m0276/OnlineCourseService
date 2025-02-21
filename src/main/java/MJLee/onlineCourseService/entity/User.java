package MJLee.onlineCourseService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class User {
    @Id
    String email;

    @Column
    String password;

    @Column
    LocalDateTime createdAt;

    @Column(unique = true)
    String nickname;
}
