/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.server.ServerCommands;
import si.isae.edu.lb.accov_1064n_2019.server.controller.ServerController;
import si.isae.edu.lb.accov_1064n_2019.server.interfaces.CommandsIface;

/**
 *
 * @author Aanthony
 */
public class ServerModel {
    private ServerSocket serverSocet;
    private Object sync = new Object();
    private Object sync1 = new Object();
    private int port;
    private String name;
    private List<ClientSocket> clients;
    private CommandsIface commandsIface;

    public List<ClientSocket> getClients() {
        return clients;
    }

    public void setClients(List<ClientSocket> clients) {
        this.clients = clients;
    }
    private String workingCommand;
    public ServerSocket getServerSocet() {
        return serverSocet;
    }

    public void setServerSocet(ServerSocket serverSocet) {
        this.serverSocet = serverSocet;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ServerModel(String name , int port){
        this.name = name;
        this.port = port;
        this.commandsIface = new ServerController();
        this.clients = new ArrayList<ClientSocket>();
        try {
            this.serverSocet = new ServerSocket(this.port);
        } catch (IOException ex) {
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //messages that i get from ClientModel
    public void listen() throws IOException{
        while(true){
            //set the string working command
            synchronized(sync){
                Socket socket = serverSocet.accept();
                System.out.println("new conx");
                ClientModel currentModel = null;
                 try {
                     currentModel = getInput(socket);
                 } catch (Exception ex) {
                     System.out.println("exception");
                     ex.printStackTrace();
                 }
                 if(currentModel == null){
                     System.out.println("current model null");
                 }else{
                     System.out.println("current model "+currentModel.getName());
                 }
                 //error in sending data ignore
                if(currentModel == null || currentModel.getCommand() == null || currentModel.getCommand().length() <= 0 )continue;
                
                ClientSocket clientSocket = new ClientSocket(currentModel, socket);
                
                System.out.println("current model "+currentModel.getName());
                if(currentModel.getCommand().equals(ServerCommands._CONNECT.toString())){
                    //new user todo for loop to check the name
                    System.out.println("connecting client");
                    commandsIface._connectClient(clientSocket,this);

                    System.out.println("after connecting client");
                    System.out.println("clients size:"+clients.size());
                    continue;
                }
                
                if(currentModel.getCommand().equals(ServerCommands._WHO.toString())){
                    //list users
                    System.out.println("who client");
                    commandsIface._whoClient(this,clientSocket);
                    clientSocket.closeSocket();
                    System.out.println("who client");
                    continue;
                }
                
                if(currentModel.getCommand().equals(ServerCommands._QUIT.toString())){
                    commandsIface._quitClient(this,clientSocket);
                    clientSocket.closeSocket();
                    continue;
                }
            }
        }
    }
    
    
//    Messages that i get from myself like the keyboard handler in clientSide
    public void talk(Scanner keyboard){
        while(true){
            //set the string working command
            synchronized(sync){
                String message = keyboard.next();
                 if(message.equals(ServerCommands._KILL.toString())){
                     commandsIface._killClient(null, this);
                     continue;
                 }
                 if(message.equals(ServerCommands._SHUTDOWN.toString())){
                     commandsIface._shutDownServer(this);
                     continue;
                 }
                 if(message.equals(ServerCommands._WHO.toString())){
                     commandsIface._whoServer(this);
                     continue;
                 }
            }
        }    
    }
    
    public void informAboutNewClient(String clientName){
       for(ClientSocket client: this.clients){
           client.getClientModel().setMessageFromServer("new client arrived "+ clientName);
           this.sendDataToClient(client);
       }
    }
    
    public void informAbouteftClient(String clientName) {
        for(ClientSocket client: this.clients){
           client.getClientModel().setMessageFromServer("client left "+ clientName);
           this.sendDataToClient(client);
       }
    }
    
    public void informAboutWho(ClientSocket clientSocket){
        String messageFromServer ="The server contains:";
        int i =0 ;
        for(ClientSocket client: this.clients){
        if(i%5 == 0)messageFromServer+="\n";
        if(i == 0)messageFromServer+="{ ";
        messageFromServer+=client.getClientModel().getName();
        if(i == clients.size()-1) messageFromServer+=" }";
        else messageFromServer+=" , ";
        i++;
        }
        clientSocket.getClientModel().setMessageFromServer(messageFromServer);
        this.sendDataToClient(clientSocket);
    }
    
    private void sendDataToClient(ClientSocket clientSocket){
        try {
            ObjectOutputStream oos = getOutput(clientSocket.getSocket());
            oos.writeObject(clientSocket.getClientModel());
            oos.flush();// never forget to flush kidss
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //get socket readers and writers from DR pascal course youtube for Client Model
    private ClientModel getInput(Socket clientSocket) throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        ois = new  ObjectInputStream(clientSocket.getInputStream());
        return  (ClientModel) ois.readObject();
    }
          //changed in it so i can send the object ClientModel
        public ObjectOutputStream getOutput(Socket clientSocket)throws IOException{
            return new ObjectOutputStream(clientSocket.getOutputStream());
        }

 
}
