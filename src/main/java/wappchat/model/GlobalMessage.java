package wappchat.model;

import lombok.Builder;
import lombok.Getter;

public class GlobalMessage extends Message{
    public GlobalMessage(String text, String date, String sender, MessageType type) {
        super(text, date, sender, type);
    }
}
