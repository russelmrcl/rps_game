import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    // Store the player name
    private static String playerName;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String input;

        // Create a socket connection to the server running on localhost and port 1337
        Socket server = new Socket("localhost", 1337);

        // Set up input and output streams to communicate with the server
        BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(server.getOutputStream(), true);
        BufferedReader inputServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

        // Welcome message and get the player's name
        System.out.println("Welcome to rock paper scissors!");
        System.out.println("Enter your name :");
        playerName = scanner.nextLine();

        // Game loop: continuously prompt the player for their choice until the game is over
        while (true) {
            do {
                // Ask the player to select rock, paper, or scissors
                System.out.println("Select: [R]ock, [P]aper, or [S]cissors");
                input = inputUser.readLine().toLowerCase();

            } while (!input.equals("r") && !input.equals("p") && !input.equals("s"));

            // Send the player's name and choice to the server
            writer.println(playerName + ": " + input);

            // Wait for response from the server and display it
            System.out.println("Wait for response");
            String serverResponse = inputServer.readLine();
            System.out.println("Result: " + serverResponse);

            // Terminate the client if the server response is null (i.e., the server connection is closed)
            if (serverResponse == null) {
                break;
            }

            // Exit the loop (end the game) after one round
            break;
        }

        // Close the socket and end the program
        server.close();
    }
}