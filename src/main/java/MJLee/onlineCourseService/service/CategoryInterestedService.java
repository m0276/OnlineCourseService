package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.CategoryInterestedDto;
import MJLee.onlineCourseService.entity.CategoryInterested;
import MJLee.onlineCourseService.repository.CategoryInterestedRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CategoryInterestedService {
    CategoryInterestedRepository repository;

    @Autowired
    public CategoryInterestedService(CategoryInterestedRepository repository) {
        this.repository = repository;
    }

    public void save(CategoryInterestedDto dto) {
        CategoryInterested interested = new CategoryInterested();
        interested.setCategory(dto.getCourseId());
        interested.setUserEmail(dto.getUserEmail());

        repository.save(interested);
    }

//    public boolean updateClickCount(int courseId) {
//        try {
//            // 해당 courseId로 카운트 값을 증가시킴
//            repository.updateClickCount(courseId);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
