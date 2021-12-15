package wappchat.controller;

import wappchat.model.Server;
import wappchat.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
    
    ModelAndView modelAndView = new ModelAndView();
    Server server = new Server();

    @RequestMapping("/")
    public ModelAndView welcome() {
        modelAndView.addObject("invalidInput", false);
        modelAndView.setViewName("view/index");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "myUserName", defaultValue = "") String username,
                           @RequestParam(value = "myPassword", defaultValue = "") String password) {
        if(server.getUsers().containsKey(username) && server.getUsers().get(username).getPassword().equals(password)) {
            modelAndView.setViewName("view/index");
            modelAndView.addObject("invalidInput", false);
            modelAndView.setViewName("view/chat");
            modelAndView.addObject("username", username);
            return modelAndView;
        }

        modelAndView.addObject("invalidInput", true);
        modelAndView.setViewName("view/index");
        return modelAndView;
    }

    @GetMapping("/registerview")
    public ModelAndView registerView() {
        modelAndView.setViewName("view/register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam(value = "myUserName", defaultValue = "") String username,
                           @RequestParam(value = "myPassword", defaultValue = "") String password,
                           @RequestParam(value = "confirmPassword", defaultValue = "") String confirmPassword) {

        if(!password.equals(confirmPassword)) {
            modelAndView.addObject("passwordIncorrect", true);
            modelAndView.setViewName("view/register");
            return modelAndView;
        }

        if(server.getUsers().containsKey(username)) {
            modelAndView.addObject("usernameTaken", true);
            modelAndView.setViewName("view/register");
            return modelAndView;
        }

        modelAndView.addObject("passwordIncorrect", false);
        modelAndView.addObject("usernameTaken", false);

        server.getUsers().put(username, new User(username, password));
        server.serialize();

        modelAndView.setViewName("view/index");
        return modelAndView;
    }
}