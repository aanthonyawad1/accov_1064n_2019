/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.isae.edu.lb.accov_1064n_2019.server.interfaces;

import si.isae.edu.lb.accov_1064n_2019.client.model.ClientSocket;
import si.isae.edu.lb.accov_1064n_2019.server.model.ServerModel;

/**
 *
 * @author Aanthony
 */
public interface CommandsIface {
    
    public void _connectClient(ClientSocket clientSocket,ServerModel serverModel);
    public void _shutDownServer(ServerModel serverModel);
    public void _quitClient(ServerModel serverModel, ClientSocket clientSocket);
    public void _killClient(ClientSocket clientSocket,ServerModel serverModel);
    public void _whoClient(ServerModel serverModel, ClientSocket clientSocket);
    public void _whoServer(ServerModel serverModel);
}
