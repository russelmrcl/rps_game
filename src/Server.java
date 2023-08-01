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
        ServerSocket chatServer = new ServerSocket(port);

        System.out.println("Waiting for first client...");
        clientOne = chatServer.accept();
        System.out.println("first client is connected!");

        System.out.println("Waiting for second client...");
        clientTwo = chatServer.accept();
        System.out.println("second client is connected!");

        handleCommunication();
    }

    private static void handleCommunication() throws IOException {
        BufferedReader input1 = new BufferedReader(new InputStreamReader(clientOne.getInputStream()));
        PrintWriter writer1 = new PrintWriter(clientOne.getOutputStream(), true);

        BufferedReader input2 = new BufferedReader(new InputStreamReader(clientTwo.getInputStream()));
        PrintWriter writer2 = new PrintWriter(clientTwo.getOutputStream(), true);


        while (true) {

            String messageFromClient1 = input1.readLine();
            if (messageFromClient1 == null) {
                break;
            }
            System.out.println("Received from first client: " + messageFromClient1);

            String messageFromClient2 = input2.readLine();
            if (messageFromClient2 == null) {
                break;
            }
            System.out.println("Received from second client: " + messageFromClient2);

            String[] parts1 = messageFromClient1.split(": ");
            String[] parts2 = messageFromClient2.split(": ");

            if (parts1.length == 2 && parts2.length == 2) {
                playerOneName = parts1[0];
                playerOneChoice = parts1[1];
                playerTwoName = parts2[0];
                playerTwoChoice = parts2[1];
            }

            if (playerOneChoice.equals(playerTwoChoice)) {
                writer1.println("it is a draw :|");
                writer2.println("it is a draw :|");
            }

            //PlayerOneWins
            if (playerOneChoice.equals("r") && playerTwoChoice.equals("s")
                    || playerOneChoice.equals("p") && playerTwoChoice.equals("r")
                    || playerOneChoice.equals("s") && playerTwoChoice.equals("p")) {
                writer1.println("you won :)");
                writer2.println(playerOneName + " won!" + ", you lost :(");
                //PlayerTwoWins
            } else {
                writer2.println("you won :)");
                writer1.println(playerTwoName + " won!" + ", you lost :(");
            }
        }

        clientOne.close();
        clientTwo.close();
    }
}
