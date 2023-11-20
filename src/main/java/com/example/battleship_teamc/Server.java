package com.example.battleship_teamc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                System.out.println("Server socket already open.");
                return; // Returnera om servern redan är igång
            }

            serverSocket = new ServerSocket(port);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServerSocket();
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server socket closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
