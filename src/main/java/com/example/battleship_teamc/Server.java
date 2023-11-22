package com.example.battleship_teamc;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final List<Socket> clientSockets = new ArrayList<>();
    private final List<PrintWriter> clientWriters = new ArrayList<>();
    private final int port;
    private final ExecutorService pool = Executors.newFixedThreadPool(4); // Thread pool for handling multiple clients
    private final Board board;
    private final Board tempBoard;
    private final GameController gameController;
    private final GridPane serverGrid;
    private final GridPane clientGrid;
    private final int shotDelay;
    List<String> shots;
    private ServerSocket serverSocket;

    public Server(int port, Board board, Board tempBoard, GameController gameController, GridPane serverGrid, GridPane clientGrid, int shotDelay) {
        this.port = port;
        this.board = board;
        this.tempBoard = tempBoard;
        this.gameController = gameController;
        this.serverGrid = serverGrid;
        this.clientGrid = clientGrid;
        this.shotDelay = shotDelay;
        this.shots = new ArrayList<>();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clientSockets.add(clientSocket);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            clientWriters.add(out);
            pool.execute(new ClientHandler(clientSocket)); // Handle each client in a separate thread
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error setting up streams: " + e.getMessage());
            }
        }

        public void run() {
            try {
                String inputLine;
                Random random = new Random();
                int tries = 0;

                out.println("TURN");

                while ((inputLine = in.readLine()) != null) { // Read messages from client
                    if (inputLine.equals("TURN")) {
                        serverShoot(random);
                    } else {
                        processClientShot(inputLine);
                    }
                }
                closeConnection();
            } catch (IOException e) {
                System.out.println("Error in client handler: " + e.getMessage());
            }
        }

        private void serverShoot(Random random) throws IOException {
            waitForShot();

            System.out.println("------------------\nServer Shooting");
            String message = generateUniqueCoordinates(random);

            out.println("NEXT");
            out.println(message);
            System.out.println("Shooting at: " + message);

            processShotResponse(message);

            Platform.runLater(() -> gameController.updateBoard(clientGrid, tempBoard));

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
            int row = Integer.parseInt(message.split(", ")[0]);
            int col = Integer.parseInt(message.split(", ")[1]);
            char mark = hit.equals("HIT") ? 'X' : 'O';
            tempBoard.markHit(row, col, mark);
        }

        private void processClientShot(String inputLine) {
            System.out.println("Received from client: " + inputLine);

            // Split and parse coordinates
            String[] parts = inputLine.split(", ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            // Check hit or miss and update board
            char marker = updateBoardAndCheckHit(x, y);

            // Send response back to client
            String response = (marker == 'X') ? "HIT" : "MISS";
            out.println(response);
            System.out.println("Client " + response.toLowerCase());

            // Update the JavaFX UI
            Platform.runLater(() -> gameController.updateBoard(serverGrid, board));

            // Check if the game ended
            if (board.isAllShipsSunk()) {
                if (board.isAllShipsSunk()) {
                    gameController.setWinner("Client");
                } else {
                    gameController.setWinner("Server");
                }
                System.out.println("Game ended. Winner: " + gameController.getWinner());
                closeConnection();
                Platform.runLater(() -> gameController.getWinnerLabel().setText("Winner: " + gameController.getWinner()));
            }
        }

        private char updateBoardAndCheckHit(int x, int y) {
            if (board.hasShip(x, y)) {
                board.markHit(x, y, 'X');
                return 'X';
            } else {
                board.markHit(x, y, 'O');
                return 'O';
            }
        }

        private void closeConnection() {
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}