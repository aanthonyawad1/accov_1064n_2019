/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client;

import java.io.IOException;
import si.isae.edu.lb.accov_1064n_2019.client.controller.ClientController;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.client.runnables.UnrequestedServerMessageRunnable;


/**
 *
 * @author Aanthony
 * main test test should be done the client part
 */
public class ClientSide {
    
    public static void main(String args[]) throws IOException{
        ClientController clientController = new ClientController();
        ClientModel model = new ClientModel();
        ClientSocket clientSocket = new ClientSocket(model);
       
        Thread keyboardThread = new Thread(new KeyboardHandler(clientController,clientSocket));
        Thread UnrequestedServerMessageThread = new Thread(new UnrequestedServerMessageRunnable(clientSocket));
        keyboardThread.start();
        UnrequestedServerMessageThread.start();
    }

}
