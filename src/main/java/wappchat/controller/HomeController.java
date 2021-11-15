package wappchat.controller;

import wappchat.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping("/")
    public ModelAndView welcome() {
        modelAndView.setViewName("view/index");
        return modelAndView;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        User user = new User(name);
        modelAndView.addObject(user);
        return String.format("Hello %s!", name);
    }
}