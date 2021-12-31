package wappchat.model;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@AllArgsConstructor
public class User {

    private String username;
    private ArrayList<Message> messages;

    public User(String username) {

        this.username = username;
        messages = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof User)) {
            return false;
        }

        return ((User) obj).getUsername().equals(this.username);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}