package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.CategoryInterestedDto;
import MJLee.onlineCourseService.dto.OnlineClassDto;
import MJLee.onlineCourseService.entity.CategoryInterested;
import MJLee.onlineCourseService.repository.CategoryInterestedRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
        List<CategoryInterested> list = repository.findByUsername(dto.getUsername());
        CategoryInterested categoryInterested = new CategoryInterested();

        if(list == null || list.isEmpty()){
            categoryInterested.setCourseId(dto.getCourseId());
            categoryInterested.setUsername(dto.getUsername());
            categoryInterested.setCount(1L);
        }
        else{
            for(CategoryInterested c : list){
                if(c.getCourseId().equals(dto.getCourseId())){
                    categoryInterested = c;
                    categoryInterested.setCount(c.getCount()+1);
                    break;
                }
            }

            if(categoryInterested.getCount() == null){
                categoryInterested.setCount(1L);
                categoryInterested.setCourseId(dto.getCourseId());
                categoryInterested.setUsername(dto.getUsername());
            }
        }
        repository.save(categoryInterested);
    }

    public List<String> findByUsername(String username){
        List<CategoryInterested> list = repository.findByUsername(username);
        if(list.isEmpty()) return null;

        list.sort((o1, o2) -> o1.getCount().intValue() - o2.getCount().intValue());

        List<String> returnArr = new ArrayList<>();

        for(CategoryInterested categoryInterested : list){
            returnArr.add(categoryInterested.getCourseId());
        }

        return returnArr;
    }
}
