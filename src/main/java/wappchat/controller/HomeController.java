package wappchat.controller;

import org.springframework.ui.Model;
import wappchat.model.Server;
import wappchat.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@RestController
public class HomeController {

    ModelAndView modelAndView = new ModelAndView();
    Server server = new Server();

    @RequestMapping("/")
    public ModelAndView welcome() {
        server = server.deserialize();
        modelAndView.setViewName("view/index");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String sayHello(@RequestParam(value = "myUserName", defaultValue = "") String username,
                           @RequestParam(value = "myPassword", defaultValue = "") String password) {
        User user = new User(username, password);
        modelAndView.addObject(user);
        return String.format("Hello %s!", username);
    }

    @GetMapping("/registerview")
    public ModelAndView registerView() {
        modelAndView.setViewName("view/register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam(value = "myUserName", defaultValue = "") String username,
                           @RequestParam(value = "myPassword", defaultValue = "") String password,
                           @RequestParam(value = "confirmPassword", defaultValue = "") String confirmPassowrd) {
        if(!password.equals(confirmPassowrd)) {
            modelAndView.addObject("passwordIncorrect", true);
            modelAndView.setViewName("view/register");
            return modelAndView;
        }

        if(server.getUsers().containsKey(username)) {
            modelAndView.addObject("usernameTaken", true);
            modelAndView.setViewName("view/register");
            return modelAndView;
        }

        server.getUsers().put(username, new User(username, password));
        server.serialize();

        modelAndView.setViewName("view/register");
        return modelAndView;
    }
}