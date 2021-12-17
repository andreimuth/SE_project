package wappchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import wappchat.model.Message;
import wappchat.model.Server;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{username}")
    public void sendMessage(@DestinationVariable String username, Message message) {
        boolean userExists = Server.getUsers().containsKey(username);

        if(userExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + username, message);
        }
    }

}
