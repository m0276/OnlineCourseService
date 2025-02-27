package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.entity.User;
import MJLee.onlineCourseService.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.util.StringUtils;

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
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setNickname(userDto.getNickname());
        user.setRole("ANONYMOUS");
        repository.save(user);
    }

    public UserDto findUser(UserDto userDto) {
        if(repository.findByNickname(userDto.getNickname()).isPresent()){
            User user = repository.findByNickname(userDto.getNickname()).get();
            UserDto userDto1 = new UserDto();
            userDto1.setNickname(user.getNickname());
            userDto1.setEmail(user.getEmail());
            userDto1.setPassword(user.getPassword());
            userDto1.setCreatedAt(user.getCreatedAt());
            return userDto1;
        }

        return null;
    }

    public void updateStatus(UserDto userDto) {
        //System.out.println(userDto);
        if(repository.findByNickname(userDto.getNickname()).isPresent()){
            User user = repository.findByNickname(userDto.getNickname()).get();
            if(user.getRole().equals("ANONYMOUS")) user.setRole("USER");
            else user.setRole("ANONYMOUS");
            repository.save(user);
        }
        else{
            throw new RuntimeException("Can't update");
        }
    }

    public void update(UserDto userDto) {
        if(repository.findByNickname(userDto.getNickname()).isPresent()){
            User user = repository.findByNickname(userDto.getNickname()).get();
            user.setPassword(userDto.getNewPassword());
            repository.save(user);
        }
        else{
            throw new RuntimeException("Can't update");
        }
    }

    public void delete(UserDto userDto) {
        if(repository.findByNickname(userDto.getNickname()).isPresent()){
            repository.deleteByNickname(userDto.getNickname());
            return;
        }

        throw new RuntimeException("Can't update");
    }

    private Collection<GrantedAuthority> createAuthorities(String roles){
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for(String role : roles.split(",")){
            if (!StringUtils.hasText(role)) continue;
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
