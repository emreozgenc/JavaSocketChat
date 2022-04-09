package javasocketchat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private static final int SERVER_PORT = 8888;
    private ServerSocket serverSocket;
    private List<ClientManager> managers;

    public Server() {
        managers = new LinkedList<>();
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        work();
    }

    public void sendMessageToEveryone(String message, ClientManager except) {
        for(ClientManager manager : managers) {
            if(manager != except)
                manager.sendMessageToClient(message);
        }
    }

    private void work() {
        try {
            while(true) {
                Socket client = serverSocket.accept();
                ClientManager clientManager = new ClientManager(client, this);
                managers.add(clientManager);
                clientManager.start();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public void removeClient(ClientManager client) {
        managers.remove(client);
    }
}
