package MJLee.onlineCourseService.entity;

import MJLee.onlineCourseService.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User{
    @Id
    String email;

    @Column(nullable = false)
    String password;

    @Column
    LocalDateTime createdAt;

    @Column(unique = true)
    String nickname;

    String role = String.valueOf(Role.ANONYMOUS);
}
