package com.example;

class ClientNode {
    private String Client_name;
    
    public ClientNode(String ID) {
        this.Client_name = ID;
    }
    //Return Client name
    public String Get_client_name() {
        return Client_name;
    }
    //Client sends message
    public void sends(ServerNode server, String recieveID, String message) {
        server.sendMessage(this.Client_name, recieveID, message);
    }
    
    public void recieve(String senderID, String message) {
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   MESSAGE RECEIVED                    |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println("| To:      " + this.Client_name);
        System.out.println("| From:    " + senderID);
        System.out.println("| Content: " + message);
        System.out.println("+-------------------------------------------------------+");
    }
}