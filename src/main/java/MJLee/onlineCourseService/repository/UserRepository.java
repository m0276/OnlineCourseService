package MJLee.onlineCourseService.repository;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,String> {
    Optional<User> findByNickname(String nickname);
    void deleteByNickname(String nickname);
}
