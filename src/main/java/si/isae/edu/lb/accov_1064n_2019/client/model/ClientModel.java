/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client.model;


import java.io.Serializable;

/**
 *
 * @author Aanthony
 */
public class ClientModel implements Serializable , Comparable<String>{
    private String name , machine , port;
    private boolean  isConnected;
    private String command;
    private String messageToServer;
    private String messageFromServer;

    public String getMessageFromServer() {
        return messageFromServer;
    }

    public void setMessageFromServer(String messageFromServer) {
        this.messageFromServer = messageFromServer;
    }

    public String getMessageToServer() {
        return messageToServer;
    }

    public void setMessageToServer(String messageToServer) {
        this.messageToServer = messageToServer;
    }
    
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    public boolean isIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void build(String name, String machine, String port) {
        this.name = name;
        this.machine = machine;
        this.port = port;
    }

    
    // never foget to FLUSH hihihihi to quit the network when sending data  TODO          
    public ClientModel(){
        this.isConnected = false;
        
    }

    @Override
    public String toString() {
        return "ClientModel{" + "name=" + name + ", machine=" + machine +
                ", port=" + port + '}';
    }

    @Override
    public int compareTo(String t) {
       return this.name.compareTo(t);
    }
    
}
