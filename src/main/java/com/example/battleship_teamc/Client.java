package com.example.battleship_teamc;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int MAX_RETRY_ATTEMPTS = 100;
    private final int port;
    private final String serverAddress;

    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void connectToServer() {
        int retryAttempts = 0;
        while (retryAttempts < MAX_RETRY_ATTEMPTS) { // Definiera MAX_RETRY_ATTEMPTS enligt dina behov
            try {
                Socket socket = new Socket(serverAddress, port);
                System.out.println("Connected to server: " + socket);
                return; // Anslutning lyckades, avbryt loopen
            } catch (IOException e) {
                System.out.println("Attempt " + retryAttempts + ": Server is not available. Retrying...");
                retryAttempts++;
                try {
                    Thread.sleep(1000); // Vänta en sekund innan nästa försök
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("Failed to connect to the server after " + MAX_RETRY_ATTEMPTS + " attempts.");
    }
}

    
            
        
    

