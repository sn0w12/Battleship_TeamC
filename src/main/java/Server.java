import java.io.*;
import java.net.*;
import java.util.Random;


public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Väntar på en klientanslutning...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Klient ansluten.");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String response;
            String shot = null;
            boolean gameOver = false;

            while (!gameOver) {


                if (gameOver) {
                    out.println(Protocol.GAME_OVER);
                    break;
                }

                // Skicka skottet till klienten
                out.println(shot);

                response = in.readLine(); // Ta emot svar från klienten


            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
