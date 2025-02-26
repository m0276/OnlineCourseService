package MJLee.onlineCourseService.controller;


import MJLee.onlineCourseService.dto.CategoryInterestedDto;
import MJLee.onlineCourseService.service.CategoryInterestedService;
import MJLee.onlineCourseService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CategoryInterestedController {
  CategoryInterestedService service;
  UserService userService;

  @PostMapping("/updateInterested")
  public void save(@RequestBody CategoryInterestedDto dto){
    service.save(dto);
  }

}
