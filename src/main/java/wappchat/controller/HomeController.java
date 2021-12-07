package wappchat.controller;

import wappchat.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@RestController
public class HomeController {

    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping("/")
    public ModelAndView welcome() {
        modelAndView.setViewName("view/index");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String sayHello(@RequestParam(value = "myUserName", defaultValue = "") String username,
                           @RequestParam(value = "myPassword", defaultValue = "") String password) {
        User user = new User(username, password);
        modelAndView.addObject(user);
        return String.format("Hello %s!", password);
    }

    @GetMapping("/registerview")
    public ModelAndView registerView() {
        modelAndView.setViewName("view/register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(value = "myUserName", defaultValue = "") String username,
                           @RequestParam(value = "myPassword", defaultValue = "") String password,
                           @RequestParam(value = "confirmPassword", defaultValue = "") String confirmPassowrd) {
        if(!Objects.equals(password, confirmPassowrd)) {
            return "";
        }
        User user = new User(username, password);
        modelAndView.addObject(user);
        return String.format("Hello %s!", password);
    }
}