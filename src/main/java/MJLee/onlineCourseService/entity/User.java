package MJLee.onlineCourseService.entity;

import MJLee.onlineCourseService.Role;
import MJLee.onlineCourseService.SimpleGrantedAuthority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;

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
    String username;

    @Column
    String role;

}
