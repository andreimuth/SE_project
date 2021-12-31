package wappchat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import wappchat.model.Message;
import wappchat.model.Server;
import wappchat.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;

@RestController
public class HomeController {

    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView welcome() {
        modelAndView.setViewName("view/index");
        return modelAndView;
    }


    @GetMapping("/getAllUsers")
    public HashSet<User> getAllUsers() {
        return Server.getUsers();
    }

}