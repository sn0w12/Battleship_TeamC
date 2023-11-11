package com.example.battleship_teamc;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Anslut till servern

            // Skapa en Scanner för att läsa från servern
            Scanner serverInput = new Scanner(socket.getInputStream());

            // Skapa en PrintWriter för att skicka till servern
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Skapa och initiera spelplaner, flottor
            Board playerBoard = new Board(); // Skapa en spelplan för spelaren
            Board opponentBoard = new Board(); // Skapa en spelplan för motståndaren
            Fleet playerFleet = new Fleet(); // Skapa en flotta för spelaren
            Fleet opponentFleet = new Fleet(); // Skapa en flotta för motståndaren

            Logic gameLogic = new Logic(playerBoard, opponentBoard, playerFleet, opponentFleet);

            // Spellogik för klienten
            while (gameLogic.isGameFinished()) {
                // Läs skottresultat från servern
                if (serverInput.hasNextLine()) {
                    String serverShotResult = serverInput.nextLine();
                    System.out.println("Serverns skottresultat: " + serverShotResult);
                }


                // Här skulle du använda spelets logik för att hantera serverns skott och uppdatera gameLogic

                // Klientens tur att skjuta

                int clientPlayer = 2; // Ange klientens spelar-ID (2 i det här fallet)
                String clientShot = gameLogic.randomShotAndShoot(clientPlayer); // Slumpmässigt skott och utför det
                System.out.println("Klienten skjuter: " + clientShot);
                out.println(clientShot); // Skicka skottet till servern

            }

            System.out.println("Game is over");

            // Stäng anslutningen
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
