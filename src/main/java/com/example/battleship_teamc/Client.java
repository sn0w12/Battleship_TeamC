package com.example.battleship_teamc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Client {
    private final int port;
    private final String serverAddress;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    List <String> shots;

    public Client(String serverAddress, int port, Board board) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.shots = new ArrayList<>();
    }

    public void start() throws IOException {
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Random random = new Random();
        int tries = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            while (tries < 100) {
                int row, col;
                String message;

                do {
                    row = random.nextInt(0, 10);
                    col = random.nextInt(0, 10);
                    message = row + ", " + col;
                } while (shots.contains(message)); // Keep generating new coordinates until a unique one is found

                shots.add(message); // Add the new, unique shot to the list
                System.out.println("Shooting at: " + message);

                out.println(message); // Send user input to server
                System.out.println("Server response: " + in.readLine()); // Read response from server

                tries++;
            }
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}