package MJLee.onlineCourseService.controller;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.service.LoginService;
import MJLee.onlineCourseService.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

//  @GetMapping("/login/{userName}")
//  public void login(@PathVariable String userName){
//    service.loadUserByUsername(userName);
//    //return "redirect:/";
//  }

  @PostMapping("/login")
  public String login(@RequestParam String username) {
    try {

      service.loadUserByUsername(username);
      return "redirect:/onlineClass";

    } catch (UsernameNotFoundException e) {
      return "redirect:/onlineClass/login";
    }
  }

  @GetMapping("/join")
  public String joinCreate(){
    return "joinPage";
  }

  @PostMapping("/join")
  public String join(@RequestBody UserDto userDto){
    service.join(userDto);

    return "redirect:/";
  }

}
