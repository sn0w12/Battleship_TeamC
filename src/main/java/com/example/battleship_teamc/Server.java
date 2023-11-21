
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

import static java.lang.Integer.parseInt;

public class Server {
    private ServerSocket serverSocket;
    private List<Socket> clientSockets = new ArrayList<>();
    private List<PrintWriter> clientWriters = new ArrayList<>();
    private final int port;
    private ExecutorService pool = Executors.newFixedThreadPool(4); // Thread pool for handling multiple clients
    private Board board;
    private Board tempBoard;
    private GameController gameController;
    private GridPane serverGrid;
    private GridPane clientGrid;
    private int shotDelay;
    List <String> shots;
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
        private Socket clientSocket;
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
                        System.out.println("------------------\nServer Shooting");
                        int row, col;
                        String message;

                        do {
                            row = random.nextInt(0, 10);
                            col = random.nextInt(0, 10);
                            message = row + ", " + col;
                        } while (shots.contains(message)); // Keep generating new coordinates until a unique one is found

                        shots.add(message); // Add the new, unique shot to the list

                        out.println("NEXT"); // Tell client it's about to be shot

                        out.println(message); // Send coords to client
                        System.out.println("Shooting at: " + message);

                        String hit = (in.readLine()); // Read response from server
                        if (hit.equals("HIT")) {
                            tempBoard.markHit(row, col, 'X');
                        } else
                            tempBoard.markHit(row, col, 'O');

                        Platform.runLater(() -> {
                            gameController.updateBoard(clientGrid, tempBoard);
                        });

                        out.println("TURN");
                    } else {
                        System.out.println("Received from client: " + inputLine);
                        String[] coords = inputLine.split(", ");

                        if (board.hasShip(parseInt(coords[0]), parseInt(coords[1]))) {
                            out.println("HIT"); // Send response back to client
                            System.out.println("Client hit");
                            board.markHit(parseInt(coords[0]), parseInt(coords[1]), 'X');
                        } else {
                            out.println("MISS"); // Send response back to client
                            System.out.println("Client missed");
                            board.markHit(parseInt(coords[0]), parseInt(coords[1]), 'O');
                        }

                        // Update the JavaFX UI
                        Platform.runLater(() -> {
                            gameController.updateBoard(serverGrid, board);
                        });

                        if(board.isAllShipsSunk()) {
                            System.out.println("Game ended");
                            closeConnection();
                        }

                        try {
                            Thread.sleep(shotDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                closeConnection();
            } catch (IOException e) {
                System.out.println("Error in client handler: " + e.getMessage());
            }
        }

        private void closeConnection() {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}