package wappchat.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements Serializable {

    private HashMap<String, User> users;

    public Server() {
        users = new HashMap<>();
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void serialize() {
        try {
            Socket s = new Socket("localhost", 8000);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(this);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server deserialize() {
        Server serverToReceive;
        try {
            ServerSocket server = new ServerSocket(8000);
            Socket s = server.accept();
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            serverToReceive = (Server) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            serverToReceive = new Server();
        }
        return serverToReceive;
    }
}
