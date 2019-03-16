package si.isae.edu.lb.accov_1064n_2019.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.server.ServerCommands;
import si.isae.edu.lb.accov_1064n_2019.server.controller.ServerClientLink;
import si.isae.edu.lb.accov_1064n_2019.server.exceptions.RoomCantCreateAnotherRoomException;

/**
 *
 * @author Aanthony
 */
public class ServerModel {
    protected ServerSocket serverSocket;
    protected Object sync = new Object();
    protected int port;
    protected String name;
    protected List<ServerClientLink> clientsLinks;
    protected ServerClientLink serverClientLink;


    private ArrayList<RoomServerModel> roomServerModels;




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



    public void removeClosedSocket(String name) {
        for(ServerClientLink clientLink : clientsLinks){
            if(clientLink.getClientSocket().getClientModel().getName().equals(name)){
                if(clientLink.isAlive()){clientLink.kill();}
                clientsLinks.remove(clientLink);
            }
        }
    }

    public void killClientByName(String name) {
        boolean found = false;
        for(final ServerClientLink clientLink : clientsLinks){
            if(clientLink.getClientSocket().getClientModel().getName().equals(name)){
                found =true;
                clientLink.sendMessageFromServer("Sorry but i was forced to kill you");
                //we sleep so we dont get error while writing on the thread
                try{TimeUnit.SECONDS.sleep(2);}catch (Exception e){}
                clientsLinks.remove(clientLink);
                clientLink.getClientSocket().closeSocket();
                break;
            }
        }
        if(found)
            this.informAbouteftClient(name);
    }

    public void disconnectAllActiveThreadsThenDie() {
        // inform all the sockets that i am dying
        for(ServerClientLink client: this.clientsLinks){
            if(client.isAlive()){
                client.sendMessageFromServer("say bye to me im dying");
            }
        }
        //disconnect all running threads
        for(ServerClientLink client: this.clientsLinks){
            if(client.isAlive()){
                client.getClientSocket().closeSocket();
            }
        }

        // server die
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
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



    //SERVER ROOMS FUNCTIONS
    public String addRoom(RoomServerModel roomServerModel){
        //checks if the object is a room or the main server.
        try {
            if (this instanceof RoomServerModel) throw new RoomCantCreateAnotherRoomException();
        }catch(RoomCantCreateAnotherRoomException e){
            String message = e.toString();
            System.out.println(message);
            return null;
        }

        // check if null
       if( this.roomServerModels==null)
           this.roomServerModels = new ArrayList<RoomServerModel>();
       // check if exists
       for (RoomServerModel room :this.roomServerModels){
           if(room.name.equalsIgnoreCase(roomServerModel.name)){
               System.out.println("Room name already exist,\n please ues another one.");
               return null;
           }
       }
       //create the room
        this.roomServerModels.add(roomServerModel);
       //notify all users
        String message = "new Room added with name: "+roomServerModel.name+".";
        System.out.println(message);
        //notify all subscribed users to main server
        for(ServerClientLink serverClientLink : this.clientsLinks){
            serverClientLink.sendMessageFromServer(message);
        }
        return this.listRooms();
    }

    public String listRooms(){
        //checks if the object is a room or the main server.
        try {
            if (this instanceof RoomServerModel) throw new RoomCantCreateAnotherRoomException();
        }catch(RoomCantCreateAnotherRoomException e){
            String message = e.toString();
            System.out.println(message);
            return null;
        }

        String result ="";
        int i = 1;
        for (RoomServerModel room :this.roomServerModels){
           result +=  "room "+ i + " name: "+room.name;
            i++;
        }
        return result;
    }
}
