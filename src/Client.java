import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static String playerName;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String input;
        Socket server = new Socket("localhost", 1337);

        BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(server.getOutputStream(), true);
        BufferedReader inputServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

        System.out.println("Welcome to rock paper scissors!");
        System.out.println("Enter your name :");
        playerName = scanner.nextLine();

        while (true) {
            do {
                System.out.println("Select: Rock[r], Paper[p] or Scissors[s]");
                input = inputUser.readLine();

            } while (!input.equals("r") && !input.equals("p") && !input.equals("s"));

            writer.println(playerName + ": " + input);
            // Wait for response from server and display it
            System.out.println("Wait for response");
            String serverResponse = inputServer.readLine();
            System.out.println("Result: " + serverResponse);
            // Terminate client if the server response is null (i.e., the server connection is closed)
            if (serverResponse == null) {
                break;
            }
            break;
        }
        server.close();
    }
}
