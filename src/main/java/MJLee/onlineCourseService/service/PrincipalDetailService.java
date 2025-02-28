package MJLee.onlineCourseService.service;

//import MJLee.onlineCourseService.PrincipalDetail;
import MJLee.onlineCourseService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {


  private final UserRepository userRepository;

  @Autowired
  public PrincipalDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User principal = userRepository.findByUserName(username)
        .orElseThrow(()->{
          return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
        });

    UserDetails userDts = User.builder()
        .username(principal.getUsername())
        .password(principal.getPassword())
        .roles("USER")	//마지막에 권한 주기
        .build();

    return userDts;  //시큐리티의 세션이 유저 정보가 저장이 됨.
  }
}