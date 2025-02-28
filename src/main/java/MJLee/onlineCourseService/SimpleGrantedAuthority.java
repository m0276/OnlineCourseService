package MJLee.onlineCourseService;

import java.io.Serial;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class SimpleGrantedAuthority implements GrantedAuthority {

  @Serial
  private static final long serialVersionUID = -2838163416249196275L;
  private final String role;

  public SimpleGrantedAuthority(String role) {
    Assert.hasText(role, "A granted authority textual representation is required");
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return this.role;
  }
}
