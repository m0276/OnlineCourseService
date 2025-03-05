package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.entity.User;
import MJLee.onlineCourseService.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@Service
public class UserService {
    UserRepository repository;
    //private Collection<GrantedAuthority> authorities;
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(UserDto userDto){
        if (findUser(userDto) != null){
            throw new RuntimeException("already join!");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("USER");

        repository.save(user);
    }

    public UserDto findUser(UserDto userDto) {
        if(repository.findByUsername(userDto.getUsername()).isPresent()){
            User user = repository.findByUsername(userDto.getUsername()).get();
            UserDto userDto1 = new UserDto();
            userDto1.setUsername(user.getUsername());
            userDto1.setEmail(user.getEmail());
            userDto1.setPassword(user.getPassword());
            userDto1.setCreatedAt(user.getCreatedAt());
            return userDto1;
        }

        return null;
    }

    public void update(UserDto userDto) {
        if(repository.findByUsername(userDto.getUsername()).isPresent()){
            User user = repository.findByUsername(userDto.getUsername()).get();
            user.setPassword(userDto.getNewPassword());
            repository.save(user);
        }
        else{
            throw new RuntimeException("Can't update");
        }
    }

    public void delete(UserDto userDto) {
        if(repository.findByUsername(userDto.getUsername()).isPresent()){
            repository.deleteByUsername(userDto.getUsername());
            return;
        }

        throw new RuntimeException("Can't update");
    }

//    private boolean isAuthenticated() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return false;
//        }
//        return authentication.isAuthenticated();
//    }
}
