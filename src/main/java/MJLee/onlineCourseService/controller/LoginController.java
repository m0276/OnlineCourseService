package MJLee.onlineCourseService.controller;

import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.service.PrincipalDetailService;
import MJLee.onlineCourseService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/onlineClass")
public class LoginController {

  private final UserService service;

  @GetMapping("/loginPage")
  public String loginPage(){
    return "loginPage";
  }

  @GetMapping("/join")
  public String joinCreate(){
    return "joinPage";
  }

  @PostMapping("/join")
  public String join(@RequestBody UserDto userDto){
    service.save(userDto);

    return "redirect:/";
  }



//  @PostMapping("/login")
//  public String login(@RequestParam String username, @RequestParam String password) {
//    try {
//      System.out.println(detailService.loadUserByUsername(username).getAuthorities());
//      if(isAuthenticated() || detailService.loadUserByUsername(username).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
//        return "redirect:/onlineClass";
//      }
//      else{
//        return "redirect:/onlineClass/login";
//      }
//
//    } catch (UsernameNotFoundException e) {
//      return "redirect:/onlineClass/login";
//    }
//  }


//  private boolean isAuthenticated() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//      return false;
//    }
//    return authentication.isAuthenticated();
//  }
}
