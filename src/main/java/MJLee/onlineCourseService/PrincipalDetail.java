//package MJLee.onlineCourseService;
//
//import java.io.Serial;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class PrincipalDetail implements UserDetails {
//
//  @Serial
//  private static final long serialVersionUID = 3978768593158774863L;
//  private final MJLee.onlineCourseService.entity.User user;
//
//  public PrincipalDetail(User user) {
//    this.user = (MJLee.onlineCourseService.entity.User) user;
//  }
//
//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    Collection<GrantedAuthority> collect = new ArrayList<>();
//    collect.add(() -> "ROLE_" + user.getRole());  // 예: "ROLE_USER" 형태로 권한 생성
//    return collect;
//  }
//
//  @Override
//  public String getPassword() {
//    return user.getPassword();
//  }
//
//  @Override
//  public String getUsername() {
//    return user.getUsername();
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return true;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return true;
//  }
//}
