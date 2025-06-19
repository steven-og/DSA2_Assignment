package com.example;

public class Star {
    ServerNode server;
    
    public Star() {
        server = new ServerNode();
    }
    //Insert Client
    public void insertNode(ClientNode client) {
        if (client == null) {
            System.out.println("[ERROR] Cannot add null client!");
            return;
        }
        server.addClient(client);
    }
    //Delete Client
    public void deleteNode(String ID) {
        if (ID == null || ID.trim().isEmpty()) {
            System.out.println("[ERROR] Invalid node identifier!");
            return;
        }
        
        boolean success = server.deleteClient(ID) != null;
        if (success) {
            System.out.println("[SUCCESS] Node '" + ID + "' successfully disconnected!");
        } else {
            System.out.println("[ERROR] Node '" + ID + "' not found in the network!");
        }
    }
    //Search client in the network
    public ClientNode searchClient(String clientID) {
        if (clientID == null || clientID.trim().isEmpty()) {
            System.out.println("[ERROR] Invalid node identifier for search!");
            return null;
        }
        return server.getClient(clientID);
    }
    
    //Show All clients connected to the network
    public void show() {
        ClientNode[] clients = server.clients;
        int size = clients.length;
        
        for (int i = 0; i < size; i++) {
            if (clients[i] != null) {
                System.out.println("|" + (i + 1) + "." + clients[i].Get_client_name() + "                                          |");
            }
        }
    }
    
    // Check if any nodes are connected
    public void showFormatted() {
        ClientNode[] clients = server.clients;
        boolean nodesFound = false;
        
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null) {
                nodesFound = true;
                String nodeId = clients[i].Get_client_name();
                System.out.printf("| %2d | %-49s |\n", (i + 1), nodeId);
            }
        }
        
        if (!nodesFound) {
            System.out.println("|    | No nodes currently connected to the network        |");
        }
    }
}