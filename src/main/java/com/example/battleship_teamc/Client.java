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

public class Client {
    private final int port;
    private final String serverAddress;
    private final List<String> shots;
    private final GameController gameController;
    private final Board board;
    private final Board tempBoard;
    private final GridPane serverGrid;
    private final GridPane clientGrid;
    private final int shotDelay;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String serverAddress, int port, GameController gameController, Board board, Board tempBoard, GridPane serverGrid, GridPane clientGrid, int shotDelay) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.shots = new ArrayList<>();
        this.gameController = gameController;
        this.board = board;
        this.tempBoard = tempBoard;
        this.serverGrid = serverGrid;
        this.clientGrid = clientGrid;
        this.shotDelay = shotDelay;
    }

    public void start() throws IOException {
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Random random = new Random();
        boolean endGame = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (endGame) {
                if (in.readLine().equals("TURN")) {
                    clientShoot(random);
                } else {
                    endGame = processServerShot();
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

    public void clientShoot(Random random) throws IOException {
        waitForShot();

        System.out.println("------------------\nClient Shooting");
        String message = generateUniqueCoordinates(random);
        System.out.println("Shooting at: " + message);

        out.println(message);
        processShotResponse(message);

        Platform.runLater(() -> gameController.updateBoard(serverGrid, tempBoard));

        out.println("TURN");
    }

    private void waitForShot() {
        try {
            Thread.sleep(shotDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private String generateUniqueCoordinates(Random random) {
        String message;
        int row, col;
        do {
            row = random.nextInt(0, 10);
            col = random.nextInt(0, 10);
            message = row + ", " + col;
        } while (shots.contains(message));
        shots.add(message);
        return message;
    }

    private void processShotResponse(String message) throws IOException {
        String hit = in.readLine();
        System.out.println(hit);

        int row = Integer.parseInt(message.split(", ")[0]);
        int col = Integer.parseInt(message.split(", ")[1]);
        char mark = hit.equals("HIT") ? 'X' : 'O';
        tempBoard.markHit(row, col, mark);
    }

    private boolean processServerShot() throws IOException {
        System.out.println("------------------\nClient Receiving");
        String shotInfo = readServerResponse();
        int[] coords = parseCoordinates(shotInfo);

        char marker = checkHitAndRespond(coords);
        updateBoard(coords, marker);
        if (board.isAllShipsSunk()) {
            gameController.setWinner("Server"); // or "Client" depending on your game logic
            System.out.println("Game ended. Winner: " + gameController.getWinner());
            closeConnection();
            Platform.runLater(() -> gameController.getWinnerLabel().setText("Winner: " + gameController.getWinner()));
        }
        return !board.isAllShipsSunk();

    }

    private String readServerResponse() throws IOException {
        String hit = in.readLine();
        System.out.println(hit);
        return hit;
    }

    private int[] parseCoordinates(String shotInfo) {
        String[] parts = shotInfo.split(", ");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private char checkHitAndRespond(int[] coords) throws IOException {
        boolean hit = board.hasShip(coords[0], coords[1]);
        char marker = hit ? 'X' : 'O';
        out.println(hit ? "HIT" : "MISS");
        System.out.println("Server " + (hit ? "hit" : "missed"));
        return marker;
    }

    private void updateBoard(int[] coords, char marker) {
        board.markHit(coords[0], coords[1], marker);
        Platform.runLater(() -> gameController.updateBoard(clientGrid, board));
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