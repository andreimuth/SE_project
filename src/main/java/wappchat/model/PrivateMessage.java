package wappchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PrivateMessage extends Message{

    private String receiver;


    public PrivateMessage(String text, String date, String sender, MessageType type, String receiver) {
        super(text, date, sender, type);
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }
}
