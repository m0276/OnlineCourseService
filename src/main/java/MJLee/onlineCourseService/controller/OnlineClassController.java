package MJLee.onlineCourseService.controller;

import MJLee.onlineCourseService.service.OnlineClassService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/onlineClass")
public class OnlineClassController {

    OnlineClassService onlineClassService;
    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public OnlineClassController(OnlineClassService onlineClassService,  SecurityContextRepository securityContextRepository) {
        this.onlineClassService = onlineClassService;
        this.securityContextRepository = securityContextRepository;
    }

    // 전체 조회, 카테고리별 조회, 강의명 조회, 가격, 진행주차 별로 조회 가능한 메서드 생성
    @GetMapping
    public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response, Model model) {
        SecurityContext securityContext = securityContextRepository.loadDeferredContext(request).get();
        Authentication authentication = (securityContext != null) ? securityContext.getAuthentication() : null;

        String userName = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "";

        if(!userName.isEmpty()){
            model.addAttribute("coursesList",onlineClassService.findAllWithSort(userName));
        }
        else{
            model.addAttribute("coursesList",onlineClassService.findAll());
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showInfo");

        return modelAndView;
    }


    @GetMapping("/category/{category}")
    public ModelAndView findByCategory(Model model, @PathVariable String category){
        model.addAttribute("coursesList",onlineClassService.findByCategory(category));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showInfo");

        return modelAndView;
    }

    @GetMapping("/courseName/{name}")
    public ModelAndView findByName(Model model, @PathVariable String name){
        model.addAttribute("coursesList",onlineClassService.findByName(name));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showInfo");

        return modelAndView;
    }

    @GetMapping("/fee/{fee}")
    public ModelAndView findByFee(Model model, @PathVariable String fee){
        model.addAttribute("coursesList",onlineClassService.findByFee(fee));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showInfo");

        return modelAndView;
    }

    @GetMapping("/week/{week}")
    public ModelAndView findByWeek(Model model , @PathVariable String week){
        model.addAttribute("coursesList",onlineClassService.findBySpendWeek(week));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showInfo");

        return modelAndView;
    }

}
