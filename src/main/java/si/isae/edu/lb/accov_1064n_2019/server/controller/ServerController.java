/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.server.controller;

import si.isae.edu.lb.accov_1064n_2019.client.model.ClientModel;
import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.server.interfaces.CommandsIface;
import si.isae.edu.lb.accov_1064n_2019.server.model.ServerModel;

/**
 *
 * @author Aanthony
 */
public class ServerController implements CommandsIface{

    @Override
    public void _connectClient(ClientSocket clientSocket, ServerModel serverModel) {
        serverModel.informAboutNewClient(clientSocket.getClientModel().getName());
        serverModel.getClients().add(clientSocket);
    }

    @Override
    public void _shutDownServer(ServerModel serverModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void _quitClient(ServerModel serverModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void _killClient(ClientSocket clientSocket, ServerModel serverModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void _whoClient(ServerModel serverModel, ClientSocket clientSocket) {
        serverModel.informAboutWho(clientSocket);
    }

    @Override
    public void _whoServer(ServerModel serverModel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
}
