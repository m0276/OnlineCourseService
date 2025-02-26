package MJLee.onlineCourseService.service;

import MJLee.onlineCourseService.dto.CategoryInterestedDto;
import MJLee.onlineCourseService.dto.OnlineClassDto;
import MJLee.onlineCourseService.entity.CategoryInterested;
import MJLee.onlineCourseService.repository.CategoryInterestedRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        List<CategoryInterested> list = repository.findByUserName(dto.getUserName());
        CategoryInterested categoryInterested = new CategoryInterested();
        if(list == null || list.isEmpty()){
            categoryInterested.setCourseId(dto.getCourseId());
            categoryInterested.setUserName(dto.getUserName());
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

        }
        repository.save(categoryInterested);
    }

    public List<OnlineClassDto> findByUserName(String userName){
        List<CategoryInterested> list = repository.findByUserName(userName);
        if(list.isEmpty()) return null;

        list.sort(new Comparator<CategoryInterested>() {
          @Override
          public int compare(CategoryInterested o1, CategoryInterested o2) {
            return o1.getCount().intValue() - o2.getCount().intValue();
          }
        });
        List<OnlineClassDto> returnArr = new ArrayList<>();
        for(CategoryInterested categoryInterested : list){
            OnlineClassDto dto = new OnlineClassDto();
            dto.setCourseId(categoryInterested.getCourseId());
            returnArr.add(dto);
        }

        return returnArr;
    }
}
