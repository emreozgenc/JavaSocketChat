package javasocketchat.client;

import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your name to join chat room : ");
        String name = scanner.nextLine();
        new Client(name);
    }
}
