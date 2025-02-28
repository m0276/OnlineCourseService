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
public class User extends org.springframework.security.core.userdetails.User {

    @Serial
    private static final long serialVersionUID = -7088409102501318308L;
    @Id
    String email;

    @Column(nullable = false)
    String password;

    @Column
    LocalDateTime createdAt;

    @Column(unique = true)
    String userName;

    @Column
    String role;

    public User() {
        super("Visitor", "0000", Collections.emptyList());
        this.createdAt = LocalDateTime.now();
        this.userName = "Visitor";
        this.password = "0000";
        this.role = "VISITOR";
    }

    public User(String username, String password,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
