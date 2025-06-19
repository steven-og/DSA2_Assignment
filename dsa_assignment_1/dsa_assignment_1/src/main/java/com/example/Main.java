package com.example;
import java.util.*;

public class Main {
    // Display the main menu
    static void displayMenu() {
        clearScreen();
        System.out.println("********************************************************");
        System.out.println("*                STELLAR NETWORK COMMANDER              *");
        System.out.println("*                                                       *");
        System.out.println("********************************************************");
        System.out.println("*                                                      *");
        System.out.println("*  [1] Connect New Node to Network                     *");
        System.out.println("*  [2] Disconnect Node from Network                    *");
        System.out.println("*  [3] Transmit Message Between Nodes                  *");
        System.out.println("*  [4] Display Connected Nodes                         *");
        System.out.println("*  [5] Shutdown Network                                *");
        System.out.println("*                                                      *");
        System.out.println("********************************************************");
    }
    
   
    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    static void displaySectionHeader(String title) {
        System.out.println("\n========================================================");
        System.out.println("  " + title);
        System.out.println("========================================================");
    }
    
    // Display a success message
    static void displaySuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }
    
    // Display an error message
    static void displayError(String message) {
        System.out.println("[ERROR] " + message);
    }
    
    // Display a waiting message
    static void displayWaiting(String message) {
        System.out.println("[PROCESSING] " + message);
    }

    public static void main(String[] args) {
        // Declaration of Variables for the main program
        String clientName, receiverName;
        int choiceSelected;
        Star starNetwork = new Star();
        Scanner scanner = new Scanner(System.in);

        // The program will run until exited by the user with option '5'
        while (true) {
            // Display the menu
            displayMenu();
            System.out.print("\n> Enter your command: ");
            
            // Input validation
            try {
                choiceSelected = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                displayError("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
                pressEnterToContinue(scanner);
                continue;
            }

            // Process the selected option
            switch (choiceSelected) {
                case 1: // Add client
                    clearScreen();
                    displaySectionHeader("NODE CONNECTION");
                    System.out.print("> Enter node identifier: ");
                    clientName = scanner.nextLine();
                    
                    if (clientName.trim().isEmpty()) {
                        displayError("Node identifier cannot be empty!");
                    } else {
                        displayWaiting("Establishing connection to network...");
                        try { Thread.sleep(800); } catch (InterruptedException e) { }
                        
                        ClientNode client = new ClientNode(clientName);
                        starNetwork.insertNode(client);
                        displaySuccess("Node '" + clientName + "' successfully connected to network!");
                    }
                    pressEnterToContinue(scanner);
                    break;

                case 2: // Delete client
                    clearScreen();
                    displaySectionHeader("NODE DISCONNECTION");
                    System.out.print("> Enter node identifier to disconnect: ");
                    clientName = scanner.nextLine();
                    
                    displayWaiting("Terminating connection...");
                    // Simulate processing time
                    try { Thread.sleep(800); } catch (InterruptedException e) { }
                    
                    starNetwork.deleteNode(clientName);
                    pressEnterToContinue(scanner);
                    break;

                case 3: // Send message
                    clearScreen();
                    displaySectionHeader("MESSAGE TRANSMISSION");
                    System.out.print("> Number of messages to transmit: ");
                    int numMessages;
                    try {
                        numMessages = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        if (numMessages <= 0) {
                            displayError("Number of messages must be positive!");
                            pressEnterToContinue(scanner);
                            break;
                        }
                    } catch (InputMismatchException e) {
                        displayError("Please enter a valid number!");
                        scanner.nextLine(); // Clear the invalid input
                        pressEnterToContinue(scanner);
                        break;
                    }
                    
                    // Process each message
                    for (int i = 0; i < numMessages; i++) {
                        clearScreen();
                        displaySectionHeader("MESSAGE " + (i + 1) + " OF " + numMessages);
                        System.out.print("> Source node: ");
                        clientName = scanner.nextLine();
                        System.out.print("> Destination node: ");
                        receiverName = scanner.nextLine();
                        System.out.print("> Message content: ");
                        String message = scanner.nextLine();
                        displayWaiting("Encrypting and transmitting message...");
                        try { Thread.sleep(1000); } catch (InterruptedException e) { }                      
                        // Send the message
                        starNetwork.server.sendMessage(clientName, receiverName, message);
                        if (i < numMessages - 1) {
                            pressEnterToContinue(scanner);
                        }
                    }
                    pressEnterToContinue(scanner);
                    break;

                case 4: // Show clients
                    clearScreen();
                    displaySectionHeader("CONNECTED NODES");
                    
                    displayWaiting("Scanning network...");
                    try { Thread.sleep(800); } catch (InterruptedException e) { }
                    
                    System.out.println("+----+---------------------------------------------------+");
                    System.out.println("| ID | NODE IDENTIFIER                                   |");
                    System.out.println("+----+---------------------------------------------------+");
                    
                    starNetwork.showFormatted();
                    
                    System.out.println("+----+---------------------------------------------------+");
                    pressEnterToContinue(scanner);
                    break;

                case 5: // Exit
                    clearScreen();
                    displaySectionHeader("NETWORK SHUTDOWN");
                    displayWaiting("Terminating all connections...");
                    try { Thread.sleep(1500); } catch (InterruptedException e) { }
                    
                    System.out.println("\n********************************************************");
                    System.out.println("*                                                      *");
                    System.out.println("*             NETWORK SHUTDOWN SUCCESSFUL              *");
                    System.out.println("*                                                      *");
                    System.out.println("********************************************************");
                    
                    try { Thread.sleep(1000); } catch (InterruptedException e) { }
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    displayError("Invalid command! Please select options 1-5.");
                    pressEnterToContinue(scanner);
                    break;
            }
        }
    }
    
    // Utility method to pause and wait for user input
    private static void pressEnterToContinue(Scanner scanner) {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}