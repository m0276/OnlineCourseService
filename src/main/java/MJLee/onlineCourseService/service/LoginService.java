package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

  private final UserService service;
  private final BCryptPasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto user = new UserDto();
    user.setNickname(username);
    user = service.findUser(user);
    if(user != null){
      List<GrantedAuthority> authorities = new ArrayList<>();
      return new User(user.getNickname(), user.getPassword(), authorities);
    }
    return null;
  }

  @Transactional
  public boolean join(String userEmail, String userPwd) {
    UserDto checkUser = new UserDto();
    checkUser.setEmail(userEmail);

    if (service.findUser(checkUser) != null){
      return false;
    }
    UserDto newUser = new UserDto();
    newUser.setEmail(userEmail);
    newUser.setPassword(encoder.encode(userPwd));
    service.save(newUser);
    return true;
  }
}
