package com.example.battleship_teamc;

import java.io.*;
import java.net.*;


public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Anslut till servern
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Connection established
            System.out.println("Connected to server.");

            // Prompt user for the first shot or command
            System.out.print("Press enter to start.");
            consoleInput.readLine(); // Wait for user input

            String response;
            String shot = Protocol.CLIENT_SHOT_FIRST; // Skjut första skottet

            while (true) {
                out.println(shot); // Skicka skottet till servern

                response = in.readLine(); // Ta emot svar från servern
                System.out.println("com.example.battleship_teamc.Server: " + response);

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