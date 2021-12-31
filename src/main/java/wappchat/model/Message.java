package wappchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Builder
public class Message {
    @Getter
    private String content;
    @Getter
    private String date;
    @Getter
    private String sender;
    @Getter
    private MessageType type;

    public Message(String content, String date, String sender, MessageType type) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.type = type;
    }

}
