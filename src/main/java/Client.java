import java.io.*;
import java.net.*;


public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Anslut till servern
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String response;
            String shot = Protocol.CLIENT_SHOT_FIRST; // Skjut första skottet

            while (true) {
                out.println(shot); // Skicka skottet till servern

                response = in.readLine(); // Ta emot svar från servern
                System.out.println("Server: " + response);

                if (response.equals(Protocol.GAME_OVER)) {
                    System.out.println("Du förlorade. Spelet är över.");
                    break;
                } else if (response.startsWith(Protocol.SUNK)) {

                } else {
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
