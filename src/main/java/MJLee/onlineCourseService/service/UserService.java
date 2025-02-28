package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.entity.User;
import MJLee.onlineCourseService.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@Service
public class UserService {
    UserRepository repository;
    private Collection<GrantedAuthority> authorities;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(UserDto userDto){
        if (findUser(userDto) != null){
            throw new RuntimeException("already join!");
        }

        User user = new User(userDto.getUserName(), userDto.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_VISITOR")));
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("VISITOR");

        repository.save(user);
    }

    public UserDto findUser(UserDto userDto) {
        if(repository.findByUserName(userDto.getUserName()).isPresent()){
            User user = repository.findByUserName(userDto.getUserName()).get();
            UserDto userDto1 = new UserDto();
            userDto1.setUserName(user.getUserName());
            userDto1.setEmail(user.getEmail());
            userDto1.setPassword(user.getPassword());
            userDto1.setCreatedAt(user.getCreatedAt());
            return userDto1;
        }

        return null;
    }

    public void update(UserDto userDto) {
        if(repository.findByUserName(userDto.getUserName()).isPresent()){
            User user = repository.findByUserName(userDto.getUserName()).get();
            user.setPassword(userDto.getNewPassword());
            repository.save(user);
        }
        else{
            throw new RuntimeException("Can't update");
        }
    }

    public void delete(UserDto userDto) {
        if(repository.findByUserName(userDto.getUserName()).isPresent()){
            repository.deleteByUserName(userDto.getUserName());
            return;
        }

        throw new RuntimeException("Can't update");
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
