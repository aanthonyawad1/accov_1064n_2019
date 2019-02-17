/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.MessageNotRecievedException;
import si.isae.edu.lb.accov_1064n_2019.server.ServerCommands;

/**
 *
 * @author Aanthony
 */
public class ClientSocket{
    private ClientModel clientModel;
    private Socket socket;
    private Object sync = new Object();

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }
    
    //this constructor will be used in the server for persistence
    public ClientSocket(ClientModel clientModel , Socket socket){
        this.clientModel = clientModel;
        this.socket = socket;
    }
    
    public ClientSocket(ClientModel clientModel) throws IOException{
        this.clientModel = clientModel;
        
    }
    
    
    public boolean connectToServer() {
        try {
            clientModel.setCommand(ServerCommands._CONNECT.toString());
            socket = new Socket(clientModel.getMachine(),Integer.parseInt(clientModel.getPort()));
            System.out.println("socket.get local address " +socket.getLocalAddress());
           this.informServer("hello i am "+ this.clientModel.getName());
           return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public boolean quitServer(){
        try {
            socket = new Socket(clientModel.getMachine(),Integer.parseInt(clientModel.getPort()));
            this.clientModel.setCommand(ServerCommands._QUIT.toString());
            this.informServer("Bye bye");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public boolean closeSocket(){
        try {
            if(this.socket!= null){
                this.socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void informServer(String message) throws IOException{
        clientModel.setMessageToServer(message);
        ObjectOutputStream reply = getOutput();
        reply.writeObject(clientModel);
        reply.flush();//never forget to flush kids on flush you send to server 
    }
    
    public void getWhoFromServer(){
         try {
            this.clientModel.setCommand(ServerCommands._WHO.toString());
            socket = new Socket(clientModel.getMachine(),Integer.parseInt(clientModel.getPort()));
             this.informServer(this.clientModel.getName() +" requested who");
            
            ObjectInputStream ois = getInput();
             try {
                 clientModel = (ClientModel)ois.readObject();
                 System.out.println(clientModel.getMessageFromServer());
             } catch (ClassNotFoundException ex) {
                 ex.printStackTrace();
             }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void talkToServer(String message){//todo server side
     try {
            this.clientModel.setCommand(ServerCommands._TALK.toString());
            this.informServer(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //todo figure out how use this funct needs to run in a thread alone always waiting for a message
    public void readUnrequestedServerMessage(){
        try {

            if(socket == null)throw new MessageNotRecievedException();
            ObjectInputStream ois =  getInput();
            ClientModel c= (ClientModel)ois.readObject();

            if(c == null || c.getMessageFromServer() == null || c.getMessageFromServer().length() <= 0)
                throw new MessageNotRecievedException();
            //todo check if old message
            System.out.println("the server says:\n + "+c.getMessageFromServer());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MessageNotRecievedException e) {
//            e.printStackTrace();
//            we just catch it here normal behavior to rewind the while in the thread //todo check if logic correct
        }
    }

    //get socket readers and writers from DR pascal course youtube
    public ObjectInputStream getInput() throws IOException {
        return new ObjectInputStream(
                socket.getInputStream());
    }

    //changed in it so i can send the object ClientModel
    public ObjectOutputStream getOutput()throws IOException{
        return new ObjectOutputStream(
                socket.getOutputStream());
    }


    public Object getSync() {
        return sync;
    }
}
