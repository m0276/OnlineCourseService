package MJLee.onlineCourseService.controller;


import MJLee.onlineCourseService.dto.CategoryInterestedDto;
import MJLee.onlineCourseService.service.CategoryInterestedService;
import MJLee.onlineCourseService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    Object principal;
    String userName = "";
    try{
      principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserDetails userDetails = (UserDetails)principal;
      userName = userDetails.getUsername();
    } catch (NullPointerException e){
      principal = "anonymousUser";
    }

    if(!principal.equals("anonymousUser")){
      dto.setUserName(userName);
      service.save(dto);
    }
  }

}
