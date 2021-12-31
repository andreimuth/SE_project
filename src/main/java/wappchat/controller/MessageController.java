package wappchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wappchat.model.*;

import java.util.ArrayList;
import java.util.Objects;


@RestController
public class MessageController {

    private final String globalChat = "Global";
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    public GlobalMessage sendMessage(@Payload GlobalMessage message) {
        appendMessageToAllUsers(message);
        return message;
    }

    @MessageMapping("/chat/sendPrivate/{to}")
    @SendTo("/topic/public")
    public PrivateMessage sendPrivateMessage(
                                             @Payload PrivateMessage message,
                                             @DestinationVariable String to) {
        User receiver = Server.getUserByUsername(to);
        assert receiver != null;
        appendPrivateMessage(message, Objects.requireNonNull(Server.getUserByUsername(message.getSender())), receiver);
        return message;
    }

    private void appendPrivateMessage(PrivateMessage privateMessage, User sender, User receiver) {
        receiver.getMessages().add(privateMessage);
        sender.getMessages().add(privateMessage);
    }

    public static void appendMessageToAllUsers(GlobalMessage message) {
        for(User user : Server.getUsers()) {
            user.getMessages().add(message);
        }
    }

    @MessageMapping("/chat/connect")
    @SendTo("/topic/public")
    public Message newUser(@Payload GlobalMessage message, SimpMessageHeaderAccessor headerAccessor) {
        appendMessageToAllUsers(message);
        User user = new User(message.getSender());
        Server.addUser(user);
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

    @GetMapping("/getUserMessages/{from}/{to}")
    public ArrayList<Message> getUserMessages(@PathVariable(name = "from") String from,
                                              @PathVariable(name = "to") String to) {
        User user = Server.getUserByUsername(from);
        assert user != null;
        ArrayList<Message> messages = user.getMessages();
        ArrayList<Message> result = new ArrayList<>();
        if(to.equals(globalChat)) {
            for(Message message: messages) {
                if(message instanceof GlobalMessage) {
                    result.add(message);
                }
            }
            return result;
        }

        for(Message message: messages) {
            if(message instanceof PrivateMessage) {
                if(!message.getSender().equals(from) && !message.getSender().equals(to)) {
                    continue;
                }

                if(!((PrivateMessage) message).getReceiver().equals(from) && !((PrivateMessage) message).getReceiver().equals(to)) {
                    continue;
                }
                result.add(message);
            }
        }

        return result;
    }

    public static void removePrivateMessages(User disconnectedUser) {
        for(Message message : disconnectedUser.getMessages()) {
            if(message instanceof PrivateMessage) {
                if(message.getSender().equals(disconnectedUser.getUsername())) {
                    User receiver = Server.getUserByUsername(((PrivateMessage)message).getReceiver());
                    assert receiver != null;
                    receiver.getMessages().remove(message);
                } else {
                    User sender = Server.getUserByUsername(message.getSender());
                    assert sender != null;
                    sender.getMessages().remove(message);
                }
            }
        }
    }

}
