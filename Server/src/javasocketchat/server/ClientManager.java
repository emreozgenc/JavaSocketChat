package javasocketchat.server;

import java.io.*;
import java.net.Socket;

public class ClientManager extends Thread {
    private Socket socket;
    private OutputStreamWriter streamWriter;
    private InputStreamReader streamReader;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Server server;

    public ClientManager(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        initialize();
    }

    private void initialize() {
        try {
            streamReader = new InputStreamReader(socket.getInputStream());
            streamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(streamReader);
            bufferedWriter = new BufferedWriter(streamWriter);
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void run() {
        String data = null;
        try {
            while((data = bufferedReader.readLine()) != null) {
                bufferedWriter.write(data);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                server.sendMessageToEveryone(data, this);
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        finally {
            server.removeClient(this);
            closeAll();
        }
    }

    public void sendMessageToClient(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    private void closeAll() {
        try {
            if(streamWriter != null) streamWriter.close();
            if(streamReader != null) streamReader.close();
            if(bufferedWriter != null) bufferedWriter.close();
            if(bufferedReader != null) bufferedReader.close();
            if(socket != null) socket.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
