package com.example.battleship_teamc;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Client {
    private final int port;
    private final String serverAddress;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private List <String> shots;
    private GameController gameController;
    private Board board;
    private Board tempBoard;
    private GridPane serverGrid;
    private GridPane clientGrid;

    public Client(String serverAddress, int port, GameController gameController, Board board, Board tempBoard, GridPane serverGrid, GridPane clientGrid) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.shots = new ArrayList<>();
        this.gameController = gameController;
        this.board = board;
        this.tempBoard = tempBoard;
        this.serverGrid = serverGrid;
        this.clientGrid = clientGrid;
    }

    public void start() throws IOException {
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Random random = new Random();
        boolean endGame = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while(endGame) {
                if (in.readLine().equals("TURN")) {
                    System.out.println("------------------\nClient Shooting");
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
                    String hit = (in.readLine()); // Read response from server
                    System.out.println(hit);

                    if (hit.equals("HIT")) {
                        tempBoard.markHit(row, col, 'X');
                    } else
                        tempBoard.markHit(row, col, 'O');

                    Platform.runLater(() -> {
                        gameController.updateBoard(serverGrid, tempBoard);
                    });

                    out.println("TURN");
                } else {
                    System.out.println("------------------\nClient Receiving");
                    String hit = (in.readLine()); // Read response from server
                    System.out.println(hit);
                    String[] coords = hit.split(", ");

                    if (board.hasShip(parseInt(coords[0]), parseInt(coords[1]))) {
                        out.println("HIT"); // Send response back to client
                        System.out.println("Server hit");
                        board.markHit(parseInt(coords[0]), parseInt(coords[1]), 'X');
                    } else {
                        out.println("MISS"); // Send response back to client
                        System.out.println("Server missed");
                        board.markHit(parseInt(coords[0]), parseInt(coords[1]), 'O');
                    }

                    // Update the JavaFX UI
                    Platform.runLater(() -> {
                        gameController.updateBoard(clientGrid, board);
                    });

                    if(board.isAllShipsSunk()) {
                        System.out.println("Game ended");
                        endGame = false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error in socket communication: " + e.getMessage());
            // Handle the exception as appropriate for your application
        } finally {
            // Close the socket in a controlled manner
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            }
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