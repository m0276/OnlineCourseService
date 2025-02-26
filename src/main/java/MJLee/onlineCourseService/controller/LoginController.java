package MJLee.onlineCourseService.controller;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.service.LoginService;
import MJLee.onlineCourseService.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/onlineClass")
public class LoginController {

  private final LoginService service;

  @GetMapping("/login")
  public String loginPage(){
    return "loginPage";
  }

  @GetMapping("/login/{userName}")
  public void login(@PathVariable String userName){
    service.loadUserByUsername(userName);
  }

  @PostMapping("/join")
  public void join(@RequestBody UserDto userDto){
    service.join(userDto.getEmail(), userDto.getPassword());
  }

}
