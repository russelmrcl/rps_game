import java.io.*;
import java.net.*;

public class Server {
    private static final int port = 1337;
    private static Socket clientOne;
    private static Socket clientTwo;
    private static String playerOneName;
    private static String playerTwoName;
    private static String playerOneChoice;
    private static String playerTwoChoice;

    public static void main(String[] args) throws IOException {
        // Create a server socket on port 1337 to listen for client connections
        ServerSocket chatServer = new ServerSocket(port);

        // Wait for the first client to connect
        System.out.println("Waiting for first client...");
        clientOne = chatServer.accept();
        System.out.println("First client is connected!");

        // Wait for the second client to connect
        System.out.println("Waiting for second client...");
        clientTwo = chatServer.accept();
        System.out.println("Second client is connected!");

        // Handle communication between the two clients
        handleCommunication();
    }

    private static void handleCommunication() throws IOException {
        // Set up input and output streams for the first client
        BufferedReader input1 = new BufferedReader(new InputStreamReader(clientOne.getInputStream()));
        PrintWriter writer1 = new PrintWriter(clientOne.getOutputStream(), true);

        // Set up input and output streams for the second client
        BufferedReader input2 = new BufferedReader(new InputStreamReader(clientTwo.getInputStream()));
        PrintWriter writer2 = new PrintWriter(clientTwo.getOutputStream(), true);

        while (true) {
            // Read messages from the first client
            String messageFromClient1 = input1.readLine();
            // If the message is null, it means the first client disconnected, so exit the loop
            if (messageFromClient1 == null) {
                break;
            }
            System.out.println("Received from first client: " + messageFromClient1);

            // Read messages from the second client
            String messageFromClient2 = input2.readLine();
            // If the message is null, it means the second client disconnected, so exit the loop
            if (messageFromClient2 == null) {
                break;
            }
            System.out.println("Received from second client: " + messageFromClient2);

            // Split the messages to extract player names and choices
            String[] parts1 = messageFromClient1.split(": ");
            String[] parts2 = messageFromClient2.split(": ");

            if (parts1.length == 2 && parts2.length == 2) {
                playerOneName = parts1[0];
                playerOneChoice = parts1[1];
                playerTwoName = parts2[0];
                playerTwoChoice = parts2[1];
            }

            // Determine the game result and send the appropriate messages to the clients
            if (playerOneChoice.equals(playerTwoChoice)) {
                writer1.println("It's a draw :|");
                writer2.println("It's a draw :|");
            } else if ((playerOneChoice.equals("r") && playerTwoChoice.equals("s"))
                    || (playerOneChoice.equals("p") && playerTwoChoice.equals("r"))
                    || (playerOneChoice.equals("s") && playerTwoChoice.equals("p"))) {
                writer1.println("You won :)");
                writer2.println(playerOneName + " won!" + ", you lost :(");
            } else {
                writer2.println("You won :)");
                writer1.println(playerTwoName + " won!" + ", you lost :(");
            }
        }

        // Close the sockets when the game is over
        clientOne.close();
        clientTwo.close();
    }
}