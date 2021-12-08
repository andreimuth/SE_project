package wappchat.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<Message> messages;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
}