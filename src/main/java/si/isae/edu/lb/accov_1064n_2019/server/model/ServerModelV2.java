/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.server.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.server.interfaces.CommandsIface;
import si.isae.edu.lb.accov_1064n_2019.server.util.Constants;

/**
 *
 * @author Aanthony
 */
public class ServerModelV2 implements Runnable{
     private ServerSocket serverSocket;
    private Object sync = new Object();
    private Object sync1 = new Object();
    private int port;
    private String name;
    private List<ClientSocket> clients;
    private CommandsIface commandsIface;

    @Override
    public void run() {
        while(true){
            
        }
    }
    
    public ServerModelV2(ServerSocket serverSocket) throws IOException{
        this.serverSocket = serverSocket;
    }
    
    
    
    
    
}
