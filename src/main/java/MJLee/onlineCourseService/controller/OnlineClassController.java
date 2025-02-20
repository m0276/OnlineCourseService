package MJLee.onlineCourseService.controller;

import MJLee.onlineCourseService.service.OnlineClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public OnlineClassController(OnlineClassService onlineClassService) {
        this.onlineClassService = onlineClassService;
    }

    @GetMapping
    public ModelAndView findAll(Model model){
        model.addAttribute("coursesList",onlineClassService.findAll());
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
