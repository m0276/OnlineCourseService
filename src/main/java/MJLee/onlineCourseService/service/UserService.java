package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.entity.User;
import MJLee.onlineCourseService.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class UserService {
    UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(user.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setNickname(userDto.getNickname());
        repository.save(user);
    }

    public UserDto findUser(UserDto userDto) { // 로그인 여부... 판단? 또는 유저 정보를 더 받아서 확인하기
        if(repository.findByNickname(userDto.getNickname()).isPresent()){
            User user = repository.findByNickname(userDto.getNickname()).get();
            UserDto userDto1 = new UserDto();
            userDto1.setEmail(user.getEmail());
            userDto1.setCreatedAt(user.getCreatedAt());
            return userDto1;
        }

        throw new RuntimeException("Can't find User");
    }

    public void update(UserDto userDto) {
        if(repository.findByNickname(userDto.getNickname()).isPresent()){
            User user = repository.findByNickname(userDto.getNickname()).get();
            user.setPassword(userDto.getNewPassword());
            repository.save(user);
            return;
        }

        throw new RuntimeException("Can't update");
    }

    public void delete(UserDto userDto) {
        if(repository.findByNickname(userDto.getNickname()).isPresent()){
            repository.deleteByNickname(userDto.getNickname());
            return;
        }

        throw new RuntimeException("Can't update");
    }
}
