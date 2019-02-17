package si.isae.edu.lb.accov_1064n_2019.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.server.ServerCommands;

/**
 *
 * @author Aanthony
 */
public class ServerModel {
    private ServerSocket serverSocket;
    private Object sync = new Object();
    private int port;
    private String name;
    private List<ServerClientLink> clientsLinks;
    ServerClientLink serverClientLink;


    public List<ServerClientLink>  getClientsLinks(){
        return clientsLinks;
    }

    //constructor
    public ServerModel(String name , int port){
        this.name = name;
        this.port = port;
        this.clientsLinks = new ArrayList<ServerClientLink>();
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void listen() throws IOException{
        while(true){
            //set the string working command
            synchronized(sync){
                Socket socket = serverSocket.accept();
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
                //got client socket and i have the serverModel
                ClientSocket clientSocket = new ClientSocket(currentModel, socket);
                // i can create the link
                serverClientLink = new ServerClientLink(this,clientSocket);

            }
        }
    }

    //  todo this might change
    public void talk(Scanner keyboard){
        while(true){
            //set the string working command
            synchronized(sync){
                String message = keyboard.next();
                if(message.equals(ServerCommands._KILL.toString())){
//                  todo logic
                    continue;
                }
                if(message.equals(ServerCommands._SHUTDOWN.toString())){
//                  todo logic
                    continue;
                }
                if(message.equals(ServerCommands._WHO.toString())){
//                  todo logic
                    continue;
                }
            }
        }
    }



    //these 3 functions stay here because they talk with other users
    public void informAboutNewClient(String clientName){
        for(ServerClientLink client: this.clientsLinks){
            if(client.isAlive()){
                client.sendMessageFromServer("say hello to "+clientName);
            }
        }
    }

    public void informAbouteftClient(String clientName) {
        for(ServerClientLink client: this.clientsLinks){
            if(client.isAlive()){
                client.sendMessageFromServer("say bye to "+clientName);
            }
        }
    }

    public String informAboutWho(){
        String messageFromServer ="The server contains:";
        int i = 0 ;
        for(ServerClientLink client: this.clientsLinks){
            if(i % 5 == 0)messageFromServer += "\n";
            if(i == 0)messageFromServer += "{ ";
            messageFromServer += client.getClientSocket().getClientModel().getName();
            if(i == clientsLinks.size()-1) messageFromServer+=" }";
            else messageFromServer+=" , ";
            i++;
        }
        return messageFromServer ;
    }

//
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



    public void removeClosedSocket(String name) {
        for(ServerClientLink clientLink : clientsLinks){
            if(clientLink.getClientSocket().getClientModel().getName().equals(name)){
                clientsLinks.remove(clientLink);
            }
        }
    }
}
