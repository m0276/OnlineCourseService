package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService, Serializable {

  @Serial
  private static final long serialVersionUID = 8466123378780235184L;
  private final UserService service;
  private final BCryptPasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto user = new UserDto();
    user.setNickname(username);
    user = service.findUser(user);

    if(user != null){
      service.updateStatus(user);
      return new User(user.getNickname(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
    return null;
  }

  public void join(UserDto userDto) {
    if (service.findUser(userDto) != null){
      throw new RuntimeException("already join!");
    }

    service.save(userDto);
  }

}
