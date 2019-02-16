/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import si.isae.edu.lb.accov_1064n_2019.server.ServerCommands;

/**
 *
 * @author Aanthony
 */
public class ClientSocket{
    private ClientModel clientModel;
    private Socket socket;

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
    
    //this constructor will be used in the server for persistance
    public ClientSocket(ClientModel clientModel , Socket socket){
        this.clientModel = clientModel;
        this.socket = socket;
    }
    
    public ClientSocket(ClientModel clientModel) throws IOException{
        this.clientModel = clientModel;
        
    }
    
    
    public boolean connectToServer() {
        try {
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
            this.clientModel.setCommand(ServerCommands._QUIT.toString());
            this.informServer("Bye bye");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if(this.socket!= null)
                this.socket.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            this.informServer(this.clientModel.getName() +" requested who");
            
            BufferedReader bufferedReader = getInput();
            String message = "";
            
//            while(true){
//            try{
            message+=bufferedReader.readLine();
            System.out.println(bufferedReader.readLine());
//            }catch(Exception e){break;}
//            }
             System.out.println(message);
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
    //get socket readers and writers from DR pascal course youtube
    public BufferedReader getInput() throws IOException {
        return new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
    }
    
        //changed in it so i can send the object ClientModel
        public ObjectOutputStream getOutput()throws IOException{
            return new ObjectOutputStream(
                    socket.getOutputStream());
        }
    
    
}
