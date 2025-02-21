package MJLee.onlineCourseService.controller;


import MJLee.onlineCourseService.dto.UserDto;
import MJLee.onlineCourseService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/onlineClass/user")
public class UserController {
  UserService userService;


  @GetMapping("/{userNickName}")
  public ModelAndView userInfo(@PathVariable String userNickName, Model model){
    UserDto userDto = new UserDto();
    userDto.setNickname(userNickName);
    model.addAttribute("user",userService.findUser(userDto));
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("userInfo");

    return modelAndView;
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody UserDto userDto){
    try{
      userService.save(userDto);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e){
      e.printStackTrace();
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @PutMapping
  public ResponseEntity<Void> updatePassword(@RequestBody UserDto userDto){
    try{
      userService.update(userDto);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e){
      e.printStackTrace();
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @DeleteMapping("/{userNickName}")
  public ResponseEntity<Void> delete(@PathVariable String userNickName) {
    try {
      UserDto userDto = new UserDto();
      userDto.setNickname(userNickName);
      userService.delete(userDto);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
}
