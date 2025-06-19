package com.example;

public class ServerNode {
    ClientNode[] clients;
    int size = 2;
    
    public ServerNode() {
         clients = new ClientNode[2];     
    }
    
    public void addClient(ClientNode client) {
        // Check if client already exists
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null && clients[i].Get_client_name().equals(client.Get_client_name())) {
                System.out.println("[ERROR] Node with identifier '" + client.Get_client_name() + "' already exists!");
                return;
            }
        }
        
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] == null) {
                clients[i] = client;
                break;
            }
        }
        
        // If the array is full we increase the size by doubling it
        if (clients.length == size) {
            ClientNode[] new_clients = new ClientNode[2 * size];
            System.arraycopy(clients, 0, new_clients, 0, clients.length);
            clients = new_clients;
        }
        size++;
    }
    
    public String deleteClient(String ID) {
        String remove_client = ID;
        boolean clientFound = false;
        
        // Check if client exists
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null && clients[i].Get_client_name().equals(remove_client)) {
                clientFound = true;
                break;
            }
        }
        
        if (!clientFound) {
            return null;
        }
        
        // Create new array without the client
        ClientNode[] new_clients = new ClientNode[clients.length];
        int k = 0;
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null && !clients[i].Get_client_name().equals(remove_client)) {
                new_clients[k] = clients[i];
                k++;
            }
        }
        
        clients = new_clients;
        size--;
        return remove_client;
    }
    
    public void sendMessage(String sendID, String recieveID, String message) {
        boolean receiverFound = false;
        Huff_compression messageCompressor = new Huff_compression();
        
        // First check if sender exists
        boolean senderExists = false;
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null && clients[i].Get_client_name().equals(sendID)) {
                senderExists = true;
                break;
            }
        }
        
        if (!senderExists) {
            System.out.println("[ERROR] Source node '" + sendID + "' does not exist in the network!");
            return;
        }
        
        String encoded = messageCompressor.compress(message);
        
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null) {
                String searching_ID = clients[i].Get_client_name();
                
                if (searching_ID.equals(recieveID)) {
                    ClientNode reciever = clients[i];
                    reciever.recieve(sendID, encoded);
                    receiverFound = true;
                    break;
                }
            }
        }  
        
        if (!receiverFound) {
            System.out.println("[ERROR] Destination node '" + recieveID + "' does not exist in the network!");
        } else {
            System.out.println("[SUCCESS] Message successfully transmitted!");
        }
    }

    public ClientNode getClient(String id) {
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null && clients[i].Get_client_name().equals(id)) {
                return clients[i];
            }
        }
        return null;
    }
}