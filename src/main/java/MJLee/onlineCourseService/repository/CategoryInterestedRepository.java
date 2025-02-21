package MJLee.onlineCourseService.repository;

import MJLee.onlineCourseService.entity.CategoryInterested;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryInterestedRepository extends JpaRepository<CategoryInterested, String> {

}
