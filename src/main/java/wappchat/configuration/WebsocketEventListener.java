package wappchat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import wappchat.controller.MessageController;
import wappchat.model.*;

@Component
public class WebsocketEventListener {

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebsocketConnectListener(SessionConnectedEvent event) {

    }

    @EventListener
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final User user = Server.getUserByUsername((String) headerAccessor.getSessionAttributes().get("username"));
        assert user != null;
        final GlobalMessage message = new GlobalMessage(user.getUsername() + " disconnected", "", user.getUsername(),
                MessageType.DISCONNECTED);
        Server.removeUser(user);
        MessageController.appendMessageToAllUsers(message);
        MessageController.removePrivateMessages(user);
        sendingOperations.convertAndSend("/topic/public", message);
    }

}
