package wappchat.model;

import java.util.ArrayList;

public class User {

    private int id;
    private String username;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<Message> messages;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}