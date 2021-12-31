package wappchat.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

public class Server {

    private static HashSet<User> users;

    public static HashSet<User> getUsers() {
        if(users == null) {
            users = new HashSet<>();
        }
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static User getUserByUsername(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
