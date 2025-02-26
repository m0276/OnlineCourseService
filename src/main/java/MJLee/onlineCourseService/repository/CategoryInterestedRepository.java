package MJLee.onlineCourseService.repository;

import MJLee.onlineCourseService.entity.CategoryInterested;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryInterestedRepository extends JpaRepository<CategoryInterested, String> {
  List<CategoryInterested> findByUserName(String userName);
}
