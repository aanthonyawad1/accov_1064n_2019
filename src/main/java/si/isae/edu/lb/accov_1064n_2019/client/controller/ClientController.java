/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.client.controller;

import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.AlreadyConnected;
import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.FailedToConnectException;
import si.isae.edu.lb.accov_1064n_2019.client.Exceptions.NotConnected;
import si.isae.edu.lb.accov_1064n_2019.client.interfaces.CommandsIface;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;

/**
 *
 * @author Aanthony
 */
public class ClientController implements CommandsIface{
    @Override
    public void _connect(ClientSocket clientSocket) throws AlreadyConnected, FailedToConnectException {
        if(!clientSocket.getClientModel().isIsConnected() == false){throw new AlreadyConnected();}
         
        System.out.println("initialising connection");

        if(clientSocket.connectToServer()){
            System.out.println("tu est connecter avec le nom "+
                    clientSocket.getClientModel().getName() +
                    " sur la machine: "+clientSocket.getClientModel().getMachine()
                    +" avec le port: "+clientSocket.getClientModel().getPort());
            clientSocket.getClientModel().setIsConnected(true);
        }else{
            throw  new FailedToConnectException();
        }
    }

    @Override
    public void _quit(ClientSocket clientSocket) throws NotConnected {
        if(clientSocket.getClientModel().isIsConnected() == false)throw new NotConnected();
        System.out.println("initialising quit");
        clientSocket.quitServer();
        clientSocket.getClientModel().setIsConnected(false);
        System.out.println("quitted server");
    }

    @Override
    public void _who(ClientSocket clientSocket) throws NotConnected {
        if(clientSocket.getClientModel().isIsConnected() == false)throw new NotConnected();
        System.out.println("initialising who");
        clientSocket.getWhoFromServer();
        System.out.println("done");
    }

    @Override
    public void _checkIfConnected(ClientModel clientModel) throws AlreadyConnected {
         if(clientModel.isIsConnected() == true)throw new AlreadyConnected();
    }

    @Override
    public void _talk(ClientSocket clientSocket,String message) throws NotConnected {
       clientSocket.talkToServer(message);
    }
    
}
