package javasocketchat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 8888;
    private static final String SERVER_HOST = "localhost";
    private String nickName;
    private Socket socket;
    private InputStreamReader streamReader;
    private OutputStreamWriter streamWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Scanner scanner;

    public Client(String nickName) {
        this.nickName = nickName;
        initialize();
    }

    private void initialize() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            streamReader = new InputStreamReader(socket.getInputStream());
            streamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(streamReader);
            bufferedWriter = new BufferedWriter(streamWriter);
            scanner = new Scanner(System.in);
            work();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    private void work() {

        new Thread(() -> {
            String data = null;
            try {
                while((data = bufferedReader.readLine()) != null) {
                    System.out.println(data);
                }
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
            }
        }).start();

        while (true) {
            try {
                String clientData = scanner.nextLine();
                if (clientData.equalsIgnoreCase("BYE")) {
                    socket.close();
                    break;
                }
                bufferedWriter.write(nickName + " : " + clientData);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
            }
        }

    }

}