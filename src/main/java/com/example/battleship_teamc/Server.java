package com.example.battleship_teamc;



import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Väntar på en klientanslutning...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Klient ansluten.");

            // Skapa en Scanner för att läsa från klienten
            Scanner clientInput = new Scanner(clientSocket.getInputStream());

            // Skapa en PrintWriter för att skicka till klienten
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


            // Skapa och initiera spelplaner, flottor
            Board playerBoard = new Board(); // Skapa en spelplan för spelaren
            Board opponentBoard = new Board(); // Skapa en spelplan för motståndaren
            Fleet playerFleet = new Fleet(); // Skapa en flotta för spelaren
            Fleet opponentFleet = new Fleet(); // Skapa en flotta för motståndaren

            Logic gameLogic = new Logic(playerBoard, opponentBoard, playerFleet, opponentFleet);


            // Spellogik för servern
            while (gameLogic.isGameFinished()) {
                // Serverns tur att skjuta
                int serverPlayer = 1; // Ange serverns spelar-ID (1 i det här fallet)
                String serverShotResult = gameLogic.randomShotAndShoot(serverPlayer); // Slumpmässigt skott och utför det
                out.println(serverShotResult); // Skicka skottresultatet till klienten

                // Läs svar från klienten och hantera det
                String clientResponse = clientInput.nextLine();
                System.out.println("Klientens svar: " + clientResponse);


                // Här skulle du använda spelets logik för att hantera skottet och uppdatera gameLogic

            }

            System.out.println("Game is over");

            // Stäng anslutningen
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
